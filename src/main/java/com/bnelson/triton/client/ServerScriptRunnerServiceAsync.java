package com.bnelson.triton.client;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameStatus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.HashSet;

public interface ServerScriptRunnerServiceAsync {

    void getAllGames(AsyncCallback<ArrayList<Game>> async);

    void getGameStatus(Game game, AsyncCallback<GameStatus> async);

    void getGameActions(Game game, AsyncCallback<HashSet<String>> async);

    void getConnectionOutput(Game game, AsyncCallback<String> async);

    void runAction(Game game, String actionName, AsyncCallback<Void> async);

    void sendMessageToConnection(Game game, String message, AsyncCallback<Void> async);
}
