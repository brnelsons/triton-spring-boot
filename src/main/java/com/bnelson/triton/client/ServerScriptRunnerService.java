package com.bnelson.triton.client;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;
import java.util.HashSet;

@RemoteServiceRelativePath("ServerScriptRunnerService")
public interface ServerScriptRunnerService extends RemoteService {

    ArrayList<Game> getAllGames();

    GameStatus getGameStatus(Game game);

    HashSet<String> getGameActions(Game game);

    String getConnectionOutput(Game game);

    void sendMessageToConnection(Game game, String message);

    void runAction(Game game, String actionName);

    /**
     * Utility/Convenience class.
     * Use ServerScriptRunnerService.App.getInstance() to access static instance of ServerScriptRunnerServiceAsync
     */
    class App {
        private static ServerScriptRunnerServiceAsync ourInstance = GWT.create(ServerScriptRunnerService.class);

        public static synchronized ServerScriptRunnerServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
