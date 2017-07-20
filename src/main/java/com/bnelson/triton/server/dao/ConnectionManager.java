package com.bnelson.triton.server.dao;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brnel on 6/16/2017.
 */
public class ConnectionManager {
    private Map<Game, TerminalConnection> gameConnections;

    public ConnectionManager(){
        gameConnections = new HashMap<>();
    }

    public void addConnection(Game game, TerminalConnection connection){
        gameConnections.put(game, connection);
    }

    public void addConnection(Game game, ConnectionConfig connectionconfig){
        gameConnections.put(game, new TerminalConnection(connectionconfig.getServerName(), connectionconfig.getServerSocket()));
    }

    public boolean isConnected(Game game){
        TerminalConnection connection = gameConnections.get(game);
        if(connection == null){
            return false;
        }else if(!connection.isAlive()){
            //attempt to connect
            connection.connect();
            return connection.isAlive();
        }else{
            return true;
        }
    }

    public String getConnectionOutput(Game game){
            return gameConnections.get(game).getOutput();
    }

    public boolean run(Game game, String script){
        if(isConnected(game) && script != null){
            TerminalConnection connection = gameConnections.get(game);
            connection.sendMessage(script);
            return true;
        }
        return false;
    }

    public boolean run(Game game, GameAction action){
        switch (action.getScriptType()) {
            case CONNECTION_COMMAND:
                run(game, action.getScript());
                return true;
            case LOCAL_SCRIPT:
                try {
                    Process exec = Runtime.getRuntime().exec("cmd /c \"" + action.getScript() + "\"");
                    BufferedReader in = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                    while (exec.isAlive()) {
                        if (in.ready()) {
                            String line = in.readLine();
                            if (line != null && !"".equals(line)) {
                                gameConnections.get(game).appendToOutput(line);
                            }
                        }
                    }
                    gameConnections.get(game).appendToOutput(action.getActionName() + " has completed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                throw new IllegalArgumentException("Unknown GameActionType: " + action.getActionName());
        }
    }

}
