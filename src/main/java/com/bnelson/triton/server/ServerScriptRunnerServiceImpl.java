package com.bnelson.triton.server;

import com.bnelson.triton.client.ServerScriptRunnerService;
import com.bnelson.triton.server.dao.ConnectionManager;
import com.bnelson.triton.server.dao.GameConfig;
import com.bnelson.triton.server.dao.GameConfigDAO;
import com.bnelson.triton.server.dao.GameConfigDAOImpl;
import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameAction;
import com.bnelson.triton.shared.GameStatus;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class ServerScriptRunnerServiceImpl extends RemoteServiceServlet implements ServerScriptRunnerService {

    private final ConnectionManager connectionManager;
    private final GameConfigDAO gameConfigDAO;

    public ServerScriptRunnerServiceImpl() {
        gameConfigDAO = new GameConfigDAOImpl();
        connectionManager = new ConnectionManager();
        for (Map.Entry<Game, GameConfig> config : gameConfigDAO.getGameConfigMap().entrySet()) {
            Game game = config.getKey();
            GameConfig gameConfig = config.getValue();
            connectionManager.addConnection(game, gameConfig.getConnection());
        }
    }

    @Override
    public ArrayList<Game> getAllGames() {
        return new ArrayList<>(gameConfigDAO.getGameConfigMap().keySet());
    }

    @Override
    public GameStatus getGameStatus(Game game) {
        try {
            if (connectionManager.isConnected(game)) {
                return GameStatus.RUNNING;
            } else {
                return GameStatus.STOPPED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return GameStatus.UNKNOWN;
        }
    }

    @Override
    public HashSet<String> getGameActions(Game game) {
        GameConfig gameConfig = gameConfigDAO.getGameConfigMap().get(game);
        HashSet<String> actions = new HashSet<>();
        if (gameConfig != null) {
            for (GameAction gameAction : gameConfig.getActions()) {
                actions.add(gameAction.getActionName());
            }
        }
        return actions;
    }

    @Override
    public String getConnectionOutput(Game game) {
        return connectionManager.getConnectionOutput(game);
    }

    @Override
    public void sendMessageToConnection(Game game, String message) {
        connectionManager.run(game, message);
    }

    @Override
    public void runAction(Game game, String actionName) {
        GameConfig gameConfig = gameConfigDAO.getGameConfigMap().get(game);
        if (gameConfig != null) {
            for (GameAction action : gameConfig.getActions()) {
                if (actionName.equals(action.getActionName())) {
                    connectionManager.run(game, action);
                    break;
                }
            }
        }
    }

}