package com.bnelson.triton.server.service;

import com.bnelson.triton.server.connection.ConnectionManager;
import com.bnelson.triton.server.connection.SocketConnection;
import com.bnelson.triton.server.data.GameConfigurationDAO;
import com.bnelson.triton.server.data.OutputDelegate;
import com.bnelson.triton.server.data.SingleStringOutput;
import com.bnelson.triton.server.pojo.Command;
import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.server.pojo.ServerInfo;
import com.bnelson.triton.server.script.BatchScriptRunner;
import com.bnelson.triton.server.script.ScriptUtil;
import com.bnelson.triton.server.util.ConversionUtil;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
import com.bnelson.triton.shared.rpc.ExternalLinkRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    private final GameConfigurationDAO gameConfigDAO;
    private final ConnectionManager connectionManager;
    private final Map<String, OutputDelegate> gameIdOutputMap;

    @Autowired
    public GameService(GameConfigurationDAO gameConfigDAO,
                       ConnectionManager connectionManager) {
        this.gameConfigDAO = gameConfigDAO;
        this.connectionManager = connectionManager;
        gameIdOutputMap = new HashMap<>();
    }

    public ServerInfoRPC getServerInfo(String gameId) {
        ServerInfo serverInfo = gameConfigDAO.getServerInfo(gameId);
        return ConversionUtil.convert(serverInfo);
    }

    public List<GameInfoRPC> getAllGames() {
        return Lists.transform(gameConfigDAO.getAllGameInfos(), ConversionUtil::convert);
    }

    public List<CommandInfoRPC> getCommands(String gameId) {
        return Lists.transform(gameConfigDAO.getCommands(gameId), ConversionUtil::convert);
    }

    public List<ExternalLinkRPC> getExternalLinks(String gameId) {
        return Lists.transform(gameConfigDAO.getExternalLinks(gameId), ConversionUtil::convert);
    }

    public boolean refresh() {
        return gameConfigDAO.refresh()
                && connectionManager.closeAllConnections()
                && connectAll();
    }

    public boolean connectAll() {
        boolean allConnected = true;
        for (GameInfo gameInfo : gameConfigDAO.getAllGameInfos()) {
            ServerInfo serverInfo = gameConfigDAO.getServerInfo(gameInfo.getId());
            if (serverInfo != null && serverInfo.getAddress() != null) {
                allConnected = allConnected
                        && connectionManager.add(gameInfo, new SocketConnection(serverInfo.getAddress(), serverInfo.getPort()));
            }
        }
        return allConnected;
    }

    public ConnectionStatusRPC getServerStatus(String gameId) {
        if (isLocalProcess(gameId)) {
            String localProcessName = gameConfigDAO.getServerInfo(gameId).getLocalProcessName();
            BatchScriptRunner runner = new BatchScriptRunner(ScriptUtil.getIsProcessRunningString(localProcessName));
            runner.run();
            OutputDelegate output = new OutputDelegate(100);
            output.addOutput(runner.getOutput());
            boolean isRunningLocally = ScriptUtil.isProcessRunningFromResult(output.read());
            if (isRunningLocally) {
                return ConnectionStatusRPC.RUNNING;
            }
        }
        return connectionManager.getConnectionStatus(gameId);
    }

    private boolean isLocalProcess(String gameId) {
        ServerInfo serverInfo = gameConfigDAO.getServerInfo(gameId);
        return (Strings.isNullOrEmpty(serverInfo.getAddress())
                || serverInfo.getAddress().contains("localhost")
                || serverInfo.getAddress().contains("127.0.0.1"))
                && Strings.isNullOrEmpty(serverInfo.getLocalProcessName());
    }

    public String getGameOutput(String gameId) {
        OutputDelegate outputDelegate = gameIdOutputMap.get(gameId);
        if (outputDelegate != null) {
            return outputDelegate.read();
        }
        return ".";
    }

    public void runCommand(String gameId, String commandName) {
        Command command = gameConfigDAO.getCommandByName(gameId, commandName);
        OutputDelegate outputDelegate = gameIdOutputMap.get(gameId);
        switch (command.getType()) {
            case LOCAL_SCRIPT:
                BatchScriptRunner batchScriptRunner = new BatchScriptRunner(command.getValue());
                batchScriptRunner.run();
                if (outputDelegate == null) {
                    outputDelegate = new OutputDelegate(10);
                    gameIdOutputMap.put(gameId, outputDelegate);
                }
                outputDelegate.addOutput(new SingleStringOutput("command is running..."));
                outputDelegate.addOutput(batchScriptRunner.getOutput());
                break;
            default:
                outputDelegate.addOutput(
                        new SingleStringOutput("operation not ready to use..." + command.getType().name())
                );
                break;
        }
    }


}