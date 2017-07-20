package com.bnelson.triton.server.dao;

import com.bnelson.triton.shared.Game;
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
    public ResponseEntity<List<Game>> getGames() {
        final ArrayList<Game> games = gameService.getAllGames();
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache()) // if we don't return this the browser could (edge does) cache the request
                .body(games);
    }

}
