package com.bnelson.triton.server.pojo;

/**
 * Created by brnel on 7/20/2017.
 */
public class ExampleConfigs {

    private ExampleConfigs() {
    }

    public static final String EXAMPLE_FILE_NAME = "example_game.json";

    public static GameConfiguration getExampleConfig() {
        return GameConfiguration.newBuilder()
                .setGameInfo(
                        GameInfo.newBuilder()
                                .setName("example")
                                .setDescription("example is an example game.")
                                .build())
                .setServerInfo(
                        ServerInfo.newBuilder()
                                .setAddress("127.0.0.1")
                                .build())
                .addCommand(
                        Command.newBuilder()
                                .setName(Command.Name.START)
                                .setType(Command.Type.LOCAL_SCRIPT)
                                .setDescription("Starts the server")
                                .setValue("cmd /c \"C:\\\\Commands\\start_server.bat\"")
                                .build())
                .addLink(
                        ExternalLink.newBuilder()
                                .setName("Server Manager")
                                .setUrl("http://localhost:8080")
                                .build())
                .build();
    }

}
