package com.bnelson.triton.server.controller;

import com.bnelson.triton.server.service.GameService;
import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bnelson on 7/20/2017.
 */
@RestController
@RequestMapping("/rest/games")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GameInfoRPC>> getGames() {
        final ArrayList<GameInfoRPC> games = gameService.getAllGames();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

}
