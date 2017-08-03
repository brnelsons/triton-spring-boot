package com.bnelson.triton.server.connection;

import com.bnelson.triton.server.data.OutputDelegate;
import com.bnelson.triton.server.data.OutputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by brnel on 7/21/2017.
 */
public class SocketConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketConnection.class);

    private final String url;
    private final int port;

    private Socket sock = null;
    private BufferedReader br = null;
    private PrintWriter pw = null;

    public SocketConnection(String url, int port){
        this.url = url;
        this.port = port;
    }

    public boolean isConnected(){
        return sock != null && sock.isConnected();
    }

    public void connect() throws IOException {
        try {
            sock = new Socket(url, port);
            pw = new PrintWriter(sock.getOutputStream(), true);
            br = new BufferedReader(
                    new InputStreamReader(
                            sock.getInputStream(),
                            Charset.forName("UTF-8")
                    )
            );
        }catch (ConnectException e){
//            LOGGER.info("server is down. cannot connect");
        }
    }

    public void disconnect() throws IOException {
        if(sock != null){
            sock.close();
        }
    }

    public OutputDelegate.Output getOutput(){
        return new OutputReader(br);
    }

    public void write(String out){
        if(isConnected() && pw != null){
            pw.write(out);
        }
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }
}
