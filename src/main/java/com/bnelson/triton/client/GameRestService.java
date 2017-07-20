package com.bnelson.triton.client;

import com.bnelson.triton.shared.Game;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/rest/games")
interface GameRestService extends RestService {

    @GET
    void getGames(MethodCallback<List<Game>> callback);


}
