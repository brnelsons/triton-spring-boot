package com.bnelson.triton.server.service;

import com.bnelson.triton.server.dao.GameConfigurationDAO;
import com.bnelson.triton.server.pojo.Command;
import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.server.pojo.ServerInfo;
import com.bnelson.triton.server.util.ConversionUtil;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService{

    private final GameConfigurationDAO gameConfigDAO;

    @Autowired
    public GameService(GameConfigurationDAO gameConfigDAO) {
        this.gameConfigDAO = gameConfigDAO;
    }

    public ArrayList<GameInfoRPC> getAllGames() {
        List<GameInfo> infos = gameConfigDAO.getAllGameInfos();
        ArrayList<GameInfoRPC> rpcs = new ArrayList<>(infos.size());
        for(GameInfo info : infos){
            rpcs.add(ConversionUtil.convert(info));
        }
        return rpcs;
    }

    public ServerInfoRPC getServerInfo(GameInfoRPC rpc) {
        ServerInfo serverInfo = gameConfigDAO.getServerInfo(ConversionUtil.convert(rpc));
        return ConversionUtil.convert(serverInfo);
    }

    public ArrayList<String> getCommands(GameInfoRPC rpc){
        List<Command> commands = gameConfigDAO.getCommands(ConversionUtil.convert(rpc));
        ArrayList<String> commandNames = new ArrayList<>(commands.size());
        for(Command cmd : commands){
            commandNames.add(cmd.getName());
        }
        return commandNames;
    }

    public String getServerStatus(GameInfoRPC rpc){
        //TODO implement server status
        return null;
    }

    public boolean runCommand(GameInfoRPC rpc, String commandName){
        //TODO implement running commands
        return false;
    }



}