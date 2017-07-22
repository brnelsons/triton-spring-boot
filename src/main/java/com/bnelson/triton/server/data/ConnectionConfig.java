package com.bnelson.triton.server.data;

import java.io.Serializable;

/**
 * Created by brnel on 6/16/2017.
 */
public class ConnectionConfig implements Serializable{
    private final String serverName;
    private final int serverSocket;

    public ConnectionConfig(String serverName, int serverSocket) {
        this.serverName = serverName;
        this.serverSocket = serverSocket;
    }

    public String getServerName() {
        return serverName;
    }

    public int getServerSocket() {
        return serverSocket;
    }
}
