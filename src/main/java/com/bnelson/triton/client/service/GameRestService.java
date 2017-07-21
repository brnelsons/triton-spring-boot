package com.bnelson.triton.client.service;

import com.bnelson.triton.shared.rpc.GameInfoRPC;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/rest/games")
public interface GameRestService extends RestService {

    @GET
    void getGames(MethodCallback<List<GameInfoRPC>> callback);


}
