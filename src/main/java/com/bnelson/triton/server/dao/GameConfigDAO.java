package com.bnelson.triton.server.dao;

import com.bnelson.triton.shared.Game;

import java.util.Map;

/**
 * Created by brnel on 6/21/2017.
 */
public interface GameConfigDAO {

    void refresh();

    Map<Game, GameConfig> getGameConfigMap();

    GameConfig getGameConfig(Game game);

    void saveGameConfigs(Game game, GameConfig gameConfig);

}
