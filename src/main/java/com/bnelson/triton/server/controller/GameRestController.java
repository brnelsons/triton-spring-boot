package com.bnelson.triton.server.controller;

import com.bnelson.triton.server.service.GameService;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by bnelson on 7/20/2017.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/rest/game")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @RequestMapping(path = "all",method = RequestMethod.GET)
    public ResponseEntity<List<GameInfoRPC>> getGames() {
        final List<GameInfoRPC> games = gameService.getAllGames();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

    @RequestMapping(path = "status", method = RequestMethod.GET)
    public ResponseEntity<ServerInfoRPC> getGameStatus(GameInfoRPC infoRPC) {
        final ServerInfoRPC rpc = gameService.getServerInfo(infoRPC);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(rpc);
    }

    @RequestMapping(path = "commands", method = RequestMethod.GET)
    public ResponseEntity<List<CommandInfoRPC>> getGameCommands(GameInfoRPC infoRPC) {
        final List<CommandInfoRPC> games = gameService.getCommands(infoRPC);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

    @RequestMapping(path = "refresh",method = RequestMethod.GET)
    public ResponseEntity<Boolean> refreshGames(){
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.refresh());
    }

}
