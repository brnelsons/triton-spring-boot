package com.bnelson.triton.server.controller;

import com.bnelson.triton.server.service.GameService;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
import com.bnelson.triton.shared.rpc.ServerInfoRPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * **** Get All Games ****
     */
    @RequestMapping(path = "all", method = RequestMethod.GET)
    public ResponseEntity<List<GameInfoRPC>> getGames() {
        final List<GameInfoRPC> games = gameService.getAllGames();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

    /**
     * **** Get Status for Game ****
     */
    @RequestMapping(path = "status", method = RequestMethod.GET)
    public ResponseEntity<ConnectionStatusRPC> getGameStatus(@RequestParam(value = "gameId", required = true) String gameId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.getServerStatus(gameId));
    }

    /**
     * **** Get All Commands for Game ****
     */
    @RequestMapping(path = "commands", method = RequestMethod.GET)
    public ResponseEntity<List<CommandInfoRPC>> getGameCommands(@RequestParam(value = "gameId", required = true) String gameId) {
        final List<CommandInfoRPC> games = gameService.getCommands(gameId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

    /**
     * **** Get Connection output for game ****
     */
    @RequestMapping(path = "connection/output", method = RequestMethod.GET)
    public ResponseEntity<String> getConnectionOutput(@RequestParam(value = "gameId", required = true) String gameId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.getConnectionOutput(gameId));
    }

    /**
     * **** Run command for game ****
     */
    @RequestMapping(path = "commands/run", method = RequestMethod.GET)
    public ResponseEntity<List<CommandInfoRPC>> runCommand(@RequestParam(value = "gameId", required = true) String gameId,
                                                           @RequestParam(value = "commandName", required = true) String commandName) {
        final List<CommandInfoRPC> games = gameService.getCommands(gameId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

    /**
     * **** refresh configs and connections****
     * does not connect connections.
     */
    @RequestMapping(path = "refresh", method = RequestMethod.GET)
    public ResponseEntity<Boolean> refreshGames() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.refresh());
    }

}
