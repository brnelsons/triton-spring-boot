package com.bnelson.triton.server.connection;

import com.bnelson.triton.server.pojo.GameInfo;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

    private final Map<String, SocketConnection> gameIdConnectionMap;

    @Autowired
    public ConnectionManager() {
        gameIdConnectionMap = new HashMap<>();
    }

    public boolean add(GameInfo gameInfo, SocketConnection connection) {
        gameIdConnectionMap.put(gameInfo.getId(), connection);
        try {
            connection.connect();
        } catch (IOException e) {
            LOGGER.error("could not make connection with {}:{}", connection.getUrl(), connection.getPort(), e);
            return false;
        }
        return true;
    }

    public SocketConnection get(GameInfo gameInfo) {
        return get(gameInfo.getId());
    }

    public SocketConnection get(String gameId) {
        return gameIdConnectionMap.get(gameId);
    }

    public boolean closeConnection(String gameId) {
        boolean closed = closeConnection(gameIdConnectionMap.get(gameId));
        if (closed) {
            gameIdConnectionMap.remove(gameId);
        }
        return closed;
    }

    public boolean closeAllConnections() {
        boolean closed = true;
        for (String gameId : gameIdConnectionMap.keySet()) {
            closed = closed && closeConnection(gameId);
        }
        return closed;
    }

    private boolean closeConnection(SocketConnection connection) {
        if (connection != null
                && connection.isConnected()) {
            try {
                connection.disconnect();
            } catch (IOException e) {
                LOGGER.error("problem closing the connection", e);
                return false;
            }
        }
        return true;
    }

    public ConnectionStatusRPC getConnectionStatus(String gameId) {
        SocketConnection connection = get(gameId);
        if(connection == null || !connection.isConnected()){
            return ConnectionStatusRPC.STOPPED;
        }else if(connection.isConnected()){
            return ConnectionStatusRPC.RUNNING;
        }else{
            return ConnectionStatusRPC.UNKNOWN;
        }
    }
}
