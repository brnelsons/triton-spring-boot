package com.bnelson.triton.server.dao;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameAction;
import com.bnelson.triton.shared.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@Service
public class GameService{

    private final GameConfigDAO gameConfigDAO;
    private final ConnectionManager connectionManager;

    @Autowired
    public GameService(GameConfigDAO gameConfigDAO) {
        this.gameConfigDAO = gameConfigDAO;
        connectionManager = new ConnectionManager();
        for (Map.Entry<Game, GameConfig> config : gameConfigDAO.getGameConfigMap().entrySet()) {
            Game game = config.getKey();
            GameConfig gameConfig = config.getValue();
            connectionManager.addConnection(game, gameConfig.getConnection());
        }
    }

    public ArrayList<Game> getAllGames() {
        return new ArrayList<>(gameConfigDAO.getGameConfigMap().keySet());
    }

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

    public String getConnectionOutput(Game game) {
        return connectionManager.getConnectionOutput(game);
    }

    public void sendMessageToConnection(Game game, String message) {
        connectionManager.run(game, message);
    }


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