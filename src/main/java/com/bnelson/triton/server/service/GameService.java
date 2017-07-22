package com.bnelson.triton.server.service;

import com.bnelson.triton.server.connection.ConnectionManager;
import com.bnelson.triton.server.connection.SocketConnection;
import com.bnelson.triton.server.data.GameConfigurationDAO;
import com.bnelson.triton.server.pojo.Command;
import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.server.pojo.ServerInfo;
import com.bnelson.triton.server.script.BatchScriptRunner;
import com.bnelson.triton.server.util.ConversionUtil;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final GameConfigurationDAO gameConfigDAO;
    private final ConnectionManager connectionManager;

    @Autowired
    public GameService(GameConfigurationDAO gameConfigDAO,
                       ConnectionManager connectionManager) {
        this.gameConfigDAO = gameConfigDAO;
        this.connectionManager = connectionManager;
    }

    public ArrayList<GameInfoRPC> getAllGames() {
        List<GameInfo> infos = gameConfigDAO.getAllGameInfos();
        ArrayList<GameInfoRPC> rpcs = new ArrayList<>(infos.size());
        for (GameInfo info : infos) {
            rpcs.add(ConversionUtil.convert(info));
        }
        return rpcs;
    }

    public ServerInfoRPC getServerInfo(String gameId) {
        ServerInfo serverInfo = gameConfigDAO.getServerInfo(gameId);
        return ConversionUtil.convert(serverInfo);
    }

    public ArrayList<CommandInfoRPC> getCommands(String gameId) {
        List<Command> commands = gameConfigDAO.getCommands(gameId);
        ArrayList<CommandInfoRPC> rpcs = new ArrayList<>(commands.size());
        for (Command cmd : commands) {
            rpcs.add(ConversionUtil.convert(cmd));
        }
        return rpcs;
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
            allConnected = allConnected
                    && connectionManager.add(gameInfo, new SocketConnection(serverInfo.getAddress(), serverInfo.getPort()));
        }
        return allConnected;
    }

    public ConnectionStatusRPC getServerStatus(String gameId) {
        return connectionManager.getConnectionStatus(gameId);
    }

    public String getConnectionOutput(String gameId) {
        return connectionManager.getConnectionOutput(gameId);
    }

    public String runCommand(String gameId, String commandName) {
        Command command = gameConfigDAO.getCommandByName(gameId, commandName);
        if (command.getType() == Command.Type.LOCAL_SCRIPT) {
            BatchScriptRunner batchScriptRunner = new BatchScriptRunner(command.getValue());
            batchScriptRunner.run();
            return batchScriptRunner.getOutput();
        }
        switch (command.getType()) {
            case LOCAL_SCRIPT:
                BatchScriptRunner batchScriptRunner = new BatchScriptRunner(command.getValue());
                batchScriptRunner.run();
                return batchScriptRunner.getOutput();
            default:
                return "operation not ready to use..." + command.getType();
        }
    }


}