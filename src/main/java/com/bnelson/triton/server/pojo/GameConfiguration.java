package com.bnelson.triton.server.pojo;


import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brnel on 7/20/2017.
 */
public class GameConfiguration implements Serializable {
    private final GameInfo gameInfo;
    private final ServerInfo serverInfo;
    private final List<Command> commands;

    public GameConfiguration(GameInfo gameInfo,
                             ServerInfo serverInfo,
                             List<Command> commands) {
        this.gameInfo = gameInfo;
        this.serverInfo = serverInfo;
        this.commands = commands;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private GameInfo gameInfo;
        private ServerInfo serverInfo;
        private List<Command> commands;

        private Builder(){
            commands = new ArrayList<>();
        }

        public Builder setGameInfo(GameInfo gameInfo) {
            this.gameInfo = gameInfo;
            return this;
        }

        public Builder setServerInfo(ServerInfo serverInfo) {
            this.serverInfo = serverInfo;
            return this;
        }

        public Builder setCommands(List<Command> commands) {
            this.commands = commands;
            return this;
        }

        public Builder addCommand(Command command) {
            this.commands.add(command);
            return this;
        }

        public GameConfiguration build(){
            Preconditions.checkNotNull(gameInfo, "gameInfo cannot be null");
            Preconditions.checkNotNull(serverInfo, "serverInfo cannot be null");
            Preconditions.checkArgument(!commands.isEmpty(), "commands cannot be empty");

            return new GameConfiguration(gameInfo, serverInfo, commands);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameConfiguration that = (GameConfiguration) o;

        if (gameInfo != null ? !gameInfo.equals(that.gameInfo) : that.gameInfo != null) return false;
        if (serverInfo != null ? !serverInfo.equals(that.serverInfo) : that.serverInfo != null) return false;
        return commands != null ? commands.equals(that.commands) : that.commands == null;

    }

    @Override
    public int hashCode() {
        int result = gameInfo != null ? gameInfo.hashCode() : 0;
        result = 31 * result + (serverInfo != null ? serverInfo.hashCode() : 0);
        result = 31 * result + (commands != null ? commands.hashCode() : 0);
        return result;
    }
}
