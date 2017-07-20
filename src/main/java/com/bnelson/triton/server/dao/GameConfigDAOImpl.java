package com.bnelson.triton.server.dao;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameScriptType;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by brnel on 6/21/2017.
 * <p>
 * gets game configs found at provided java property config.path
 */
@Component
public class GameConfigDAOImpl implements GameConfigDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameConfigDAOImpl.class);

    private static final String GAME_CONFIG_FILE_SUFFIX = "_game.json";
    private static final String EXAMPLE_GAME_CONFIG_NAME = "example" + GAME_CONFIG_FILE_SUFFIX;

    private Map<Game, GameConfig> gameConfigMap;

    private final Environment env;
    private final Gson gson;

    @Autowired
    public GameConfigDAOImpl(Environment env,
                             @Qualifier("gson") Gson gson) {
        this.env = env;
        this.gson = gson;
    }

    @Override
    public void refresh() {
        loadAllGameConfigs();
    }

    @Override
    public Map<Game, GameConfig> getGameConfigMap() {
        if (gameConfigMap == null) {
            loadAllGameConfigs();
            return gameConfigMap;
        }
        return gameConfigMap;
    }

    private void loadAllGameConfigs() {
        String configDirectory = env.getProperty("config.dir");
        if (configDirectory == null || "".equals(configDirectory)) {
            throw new IllegalArgumentException("config.dir was not provided.");
        }
        if (!configDirectory.endsWith("\\") && !configDirectory.endsWith("/")) {
            throw new IllegalArgumentException("config.dir is not a path, must end with /(unix) or \\(windows)");
        }

        File gameConfigFolder = new File(configDirectory);
        if (!gameConfigFolder.exists()) {
            gameConfigFolder.mkdirs();
        }
        if (!gameConfigFolder.isDirectory()) {
            LOGGER.error("config.dir provided was not a directory");
            throw new IllegalArgumentException("config.dir provided was not a directory");
        }

        scanFolderForFiles(gameConfigFolder);
    }

    /**
     * TODO handle these stack traces better
     */
    private void scanFolderForFiles(File gameConfigFolder) {
        gameConfigMap = new HashMap<>();
        File[] files = gameConfigFolder.listFiles((dir, name) -> name.endsWith(GAME_CONFIG_FILE_SUFFIX));
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f == null || EXAMPLE_GAME_CONFIG_NAME.equals(f.getName())) {
                    continue;
                }
                try {
                    GameConfig gameConfig = gson.fromJson(new FileReader(f), GameConfig.class);
                    gameConfigMap.put(gameConfig.getGame(), gameConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            createExampleGameConfigJsonFile(gameConfigFolder);
        }
    }

    private void createExampleGameConfigJsonFile(File gameConfigFolder) {
        GameConfig example = GameConfig.newBuilder()
                .setGame("example")
                .setConnection("127.0.0.1", 30004)
                .addAction("Start", GameScriptType.LOCAL_SCRIPT, "E:\\Games\\steamcmd\\steamapps\\empyriondedicatedserver\\EmpyrionDedicated_NoGraphics.cmd")
                .addAction("Update", GameScriptType.LOCAL_SCRIPT, "E:\\Games\\steamcmd\\steamapps\\update_empyrion.bat")
                .addAction("Stop", GameScriptType.CONNECTION_COMMAND, "saveAndExit 0")
                .build();

        //create example gameconfig json file
        File file = new File(gameConfigFolder.getAbsolutePath() + "\\" + EXAMPLE_GAME_CONFIG_NAME);
        BufferedOutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(file));
            output.write(gson.toJson(example).getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public GameConfig getGameConfig(Game game) {
        return null;
    }

    @Override
    public void saveGameConfigs(Game game, GameConfig gameConfig) {

    }
//
//    /**
//     * Test loading game configs
//     */
//    public static void main(String[] args) {
//        GameConfigDAOImpl gameConfigDAO = new GameConfigDAOImpl();
//        gameConfigDAO.loadAllGameConfigs();
//    }
}
