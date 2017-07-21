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
import java.util.*;

/**
 * Manages reading and writing {@link GameConfiguration} to files.
 */
@Component
public class GameConfigurationDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameConfigurationDAO.class);
    private static final String FILE_SUFFIX = ".json";
    private static final String CONFIG_LOC_PROPERTY = "config.dir";
    private static final String DEFAULT_CONFIG_DIR = "./TritonConfigs/";

    private final Environment environment;
    private final Gson gson;

    private List<GameInfo> gameInfos;
    private Map<String, ServerInfo> gameIdServerInfoMap;
    private Map<String, List<Command>> gameIdCommandMap;

    @Autowired
    public GameConfigurationDAO(Environment environment,
                                @Qualifier("gson") Gson gson) {
        this.environment = environment;
        this.gson = gson;
        gameInfos = new ArrayList<>();
        gameIdServerInfoMap = new HashMap<>();
        gameIdCommandMap = new HashMap<>();
        refresh();
    }

    public List<GameInfo> getAllGameInfos() {
        return gameInfos;
    }

    public ServerInfo getServerInfo(String gameId) {
        return gameIdServerInfoMap.get(gameId);
    }

    public List<Command> getCommands(String gameId) {
        return gameIdCommandMap.get(gameId);
    }

    public boolean refresh() {
        try {
            String configLocation = environment.getProperty(CONFIG_LOC_PROPERTY, DEFAULT_CONFIG_DIR);
            Preconditions.checkNotNull(
                    configLocation,
                    CONFIG_LOC_PROPERTY + " was not found, please provide using --config.dir=/Users/<user-name>/Documents/Triton-Configs/");
            File configFile = new File(configLocation);
            if (!FileReaderWriterUtil.exists(configFile, true)
                    || configFile.list().length == 0) {
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

            gameInfos.clear();
            gameIdServerInfoMap.clear();
            gameIdCommandMap.clear();

            for (GameConfiguration config : gameConfigurations) {
                GameInfo gameInfo = config.getGameInfo();
                if(gameInfo.getId() == null){
                    gameInfo = GameInfo.newBuilder(gameInfo)
                            .setId(UUID.randomUUID().toString())
                            .build();
                }
                gameInfos.add(gameInfo);
                gameIdServerInfoMap.put(gameInfo.getId(), config.getServerInfo());
                gameIdCommandMap.put(gameInfo.getId(), config.getCommands());
            }
        }catch(Exception e){
            return false;
        }
        return true;
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
