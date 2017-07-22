package com.bnelson.triton.client.service;

import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
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
                       MethodCallback<ConnectionStatusRPC> callback);

    @GET
    @Path("commands/?gameId={gameId}")
    void getGameCommands(@PathParam("gameId") final String gameId,
                         MethodCallback<List<CommandInfoRPC>> callback);

    @GET
    @Path("connection/output/?gameId={gameId}")
    void getConnectionOutput(@PathParam("gameId") final String gameId,
                             MethodCallback<String> callback);

    @GET
    @Path("commands/?gameId={gameId}&commandName={commandName}")
    void runCommand(@PathParam("gameId") final String gameId,
                    @PathParam("commandName") final String commandName,
                    MethodCallback<String> callback);

    @GET
    @Path("refresh")
    void refreshGames(MethodCallback<Boolean> wasChangedCallback);
}
