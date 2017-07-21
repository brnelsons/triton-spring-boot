package com.bnelson.triton.client.service;

import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.GameStatusRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/rest/game")
public interface GameRestService extends RestService {

    @GET
    @Path("all")
    void getGames(MethodCallback<List<GameInfoRPC>> callback);

    @GET
    @Path("status/?gameId={gameId}")
    void getGameStatus(@PathParam("gameId") final String gameId,
                       MethodCallback<GameStatusRPC> callback);

    @GET
    @Path("commands/?gameId={gameId}")
    void getGameCommands(@PathParam("gameId") final String gameId,
                         MethodCallback<List<CommandInfoRPC>> callback);

    @GET
    @Path("refresh")
    void refreshGames(MethodCallback<Boolean> wasChangedCallback);
}
