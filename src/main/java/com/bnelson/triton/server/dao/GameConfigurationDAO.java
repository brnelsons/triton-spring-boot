package com.bnelson.triton.server.dao;

import com.bnelson.triton.server.pojo.*;
import com.bnelson.triton.server.util.FileReaderWriterUtil;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brnel on 7/20/2017.
 */
@Component
public class GameConfigurationDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameConfigurationDAO.class);
    private static final String FILE_SUFFIX = "_game.json";
    private static final String CONFIG_LOC_PROPERTY = "config.dir";

    private final Environment environment;
    private final Gson gson;

    private List<GameInfo> gameInfos;
    private Map<GameInfo, ServerInfo> serverInfoMap;
    private Map<GameInfo, List<Command>> commandsMap;

    @Autowired
    public GameConfigurationDAO(Environment environment,
                                @Qualifier("gson") Gson gson) {
        this.environment = environment;
        this.gson = gson;
        gameInfos = new ArrayList<>();
        serverInfoMap = new HashMap<>();
        commandsMap = new HashMap<>();
        refresh();
    }

    public List<GameInfo> getAllGameInfos() {
        return gameInfos;
    }

    public ServerInfo getServerInfo(GameInfo gameInfo) {
        return serverInfoMap.get(gameInfo);
    }

    public List<Command> getCommands(GameInfo gameInfo) {
        return commandsMap.get(gameInfo);
    }

    public void refresh() {
        String configLocation = environment.getProperty(CONFIG_LOC_PROPERTY);
        Preconditions.checkNotNull(
                configLocation,
                CONFIG_LOC_PROPERTY + " was not found, please provide using --config.dir=/Users/<user-name>/Documents/Triton-Configs/");
        File configFile = new File(configLocation);
        if (!FileReaderWriterUtil.exists(configFile, true) || configFile.list().length == 0) {
            createGameConfigurationfile(configLocation + ExampleConfigs.EXAMPLE_FILE_NAME, ExampleConfigs.getExampleConfig());
        }
        if (!configFile.isDirectory()) {
            throw new IllegalArgumentException(CONFIG_LOC_PROPERTY + " provided was not directory at " + configLocation);
        }
        List<GameConfiguration> gameConfigurations = new ArrayList<>();
        File[] files = configFile.listFiles((dir, name) -> name.endsWith(FILE_SUFFIX));
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f == null) {
                    continue;
                }
                gameConfigurations.add(FileReaderWriterUtil.readFromFile(f, gson, GameConfiguration.class));
            }
        }
        for (GameConfiguration config : gameConfigurations) {
            GameInfo gameInfo = config.getGameInfo();
            gameInfos.add(gameInfo);
            serverInfoMap.put(gameInfo, config.getServerInfo());
            commandsMap.put(gameInfo, config.getCommands());
        }
    }

    public void createGameConfigurationfile(String filename, GameConfiguration gameConfiguration){
        File newConfig = new File(filename);
        if(!FileReaderWriterUtil.exists(newConfig, false)){
            FileReaderWriterUtil.writeToFile(newConfig, gson, gameConfiguration);
        }else{
            LOGGER.error("Could not create file at {}, it either already exists or we cannot create it!");
        }
    }
}
