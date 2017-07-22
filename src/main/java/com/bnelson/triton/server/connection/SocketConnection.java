package com.bnelson.triton.server.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by brnel on 7/21/2017.
 */
public class SocketConnection {

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
        sock = new Socket(url, port);
        pw = new PrintWriter(sock.getOutputStream(), true);
        br = new BufferedReader(
                new InputStreamReader(
                        sock.getInputStream(),
                        Charset.forName("UTF-8")
                )
        );
    }

    public void disconnect() throws IOException {
        if(sock != null){
            sock.close();
        }
    }

    public String read() throws IOException {
        StringBuilder output = new StringBuilder();
        if(isConnected() && br != null){
            String line;
            while((line = br.readLine())!= null){
                if(!line.isEmpty()){
                    output.append(line).append("\n");
                }
            }
        }
        return output.toString();
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
