package com.bnelson.triton.server.service;

import com.bnelson.triton.server.dao.GameConfigurationDAO;
import com.bnelson.triton.server.pojo.Command;
import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.server.pojo.ServerInfo;
import com.bnelson.triton.server.util.ConversionUtil;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.GameStatusRPC;
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

    public ServerInfoRPC getServerInfo(String gameId) {
        ServerInfo serverInfo = gameConfigDAO.getServerInfo(gameId);
        return ConversionUtil.convert(serverInfo);
    }

    public ArrayList<CommandInfoRPC> getCommands(String gameId){
        List<Command> commands = gameConfigDAO.getCommands(gameId);
        ArrayList<CommandInfoRPC> rpcs = new ArrayList<>(commands.size());
        for(Command cmd : commands){
            rpcs.add(ConversionUtil.convert(cmd));
        }
        return rpcs;
    }

    public boolean refresh(){
        return gameConfigDAO.refresh();
    }

    public GameStatusRPC getServerStatus(GameInfoRPC rpc){
        //TODO implement server status
        return GameStatusRPC.UNKNOWN;
    }

    public boolean runCommand(String gameId, String commandName){
        //TODO implement running commands
        return false;
    }



}