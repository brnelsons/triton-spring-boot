package com.bnelson.triton.server.controller;

import com.bnelson.triton.server.service.GameService;
import com.bnelson.triton.shared.rpc.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/rest/game")
public class GameRestController {
    public static final Logger LOGGER = LoggerFactory.getLogger(GameRestController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private Gson gson;

    /**
     * **** Get All Games ****
     */
//    @RequestMapping(path = "all", method = RequestMethod.GET)
    @GetMapping("all")
    public ResponseEntity<List<GameInfoRPC>> getGames() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.getAllGames());
    }

    /**
     * **** Get Status for Game ****
     */
//    @RequestMapping(path = "status", method = RequestMethod.GET)
    @GetMapping("status")
    public ResponseEntity<ConnectionStatusRPC> getGameStatus(@RequestParam(value = "gameId", required = true) String gameId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.getServerStatus(gameId));
    }

    /**
     * **** Get All Commands for Game ****
     */
//    @RequestMapping(path = "commands", method = RequestMethod.GET)
    @GetMapping("commands")
    public ResponseEntity<List<CommandInfoRPC>> getGameCommands(@RequestParam(value = "gameId", required = true) String gameId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.getCommands(gameId));
    }

    /**
     * **** Get All External Links for Game ****
     */
//    @RequestMapping(path = "externalLinks", method = RequestMethod.GET)
    @GetMapping("externalLinks")
    public ResponseEntity<List<ExternalLinkRPC>> getExternalLinks(@RequestParam(value = "gameId", required = true) String gameId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(gameService.getExternalLinks(gameId));
    }

    /**
     * **** Get Connection output for game ****
     */
//    @RequestMapping(path = "output", method = RequestMethod.GET)
    @GetMapping("output")
    public ResponseEntity<String> getGameOutput(@RequestParam(value = "gameId", required = true) String gameId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(escapeJsonString(gameService.getGameOutput(gameId)));
    }

    /**
     * **** Run command for game ****
     */
//    @RequestMapping(path = "commands/run", method = RequestMethod.POST)
    @PostMapping("commands/run")
    public ResponseEntity<String> runCommand(@RequestParam(value = "gameId", required = true) String gameId,
                                             @RequestParam(value = "commandName", required = true) String commandName) {
        gameService.runCommand(gameId, commandName);
        return new ResponseEntity<>(HttpStatus.OK);// if we don't return this the browser could (edge does) cache the request
    }

    /**
     * **** refresh configs and connections****
     * does not connect connections.
     */
//    @RequestMapping(path = "refresh", method = RequestMethod.GET)
    @GetMapping("refresh")
    public ResponseEntity<String> refreshGames() {
        gameService.refresh();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String escapeJsonString(String in) {
        return gson.toJson(in);
    }
}
