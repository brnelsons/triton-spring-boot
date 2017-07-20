package com.bnelson.triton.server.dao;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameAction;
import com.bnelson.triton.shared.GameScriptType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brnel on 6/16/2017.
 */
public class GameConfig implements Serializable {
    private final Game game;
    private final ConnectionConfig connection;
    private final List<GameAction> actions;

    public GameConfig(Game game, ConnectionConfig connection, List<GameAction> actions) {
        this.game = game;
        this.connection = connection;
        this.actions = actions;
    }

    public Game getGame() {
        return game;
    }

    public ConnectionConfig getConnection() {
        return connection;
    }

    public List<GameAction> getActions() {
        return actions;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static final class Builder{

        private Game game;
        private ConnectionConfig connection;
        private List<GameAction> actions;

        private Builder(){
            actions = new ArrayList<>();
        };

        public Builder setGame(Game game) {
            this.game = game;
            return this;
        }

        public Builder setGame(String gameName) {
            this.game = new Game(gameName);
            return this;
        }

        public Builder setConnection(ConnectionConfig connection) {
            this.connection = connection;
            return this;
        }

        public Builder setConnection(String serverName, int socket) {
            connection = new ConnectionConfig(serverName, socket);
            return this;
        }

        public Builder addAction(GameAction gameAction) {
            this.actions.add(gameAction);
            return this;
        }

        public Builder addAction(String actionName, GameScriptType scriptType, String script) {
            this.actions.add(new GameAction(actionName, scriptType, script));
            return this;
        }

        public GameConfig build(){
            if(game == null){
                throw new IllegalArgumentException("Cannot build GameConfig with empty game");
            }
            if(connection == null){
                throw new IllegalArgumentException("Cannot build GameConfig with empty connection");
            }
            return new GameConfig(game,connection, actions);
        }
    }
}
