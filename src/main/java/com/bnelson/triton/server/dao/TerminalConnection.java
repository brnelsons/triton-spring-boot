package com.bnelson.triton.server.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by brnel on 6/16/2017.
 *
 * Used for making connection to telnet servers and providing output and input streams to those servers
 *
 */
public class TerminalConnection {

    enum State{
        CONNECTED, DISCONNECTED, ERROR
    }

    private final String serverName;
    private final int socketNumber;
    private Socket connection;
    private PrintWriter out;
    private BufferedReader in;
    private State connectionState = State.DISCONNECTED;
    private String errorMessage;
    private StringBuilder outputMessage = new StringBuilder("*******************************");

    public TerminalConnection(String serverName, int socketNumber) {
        this.serverName = serverName;
        this.socketNumber = socketNumber;
    }

    public void connect(){
        try{
            connection = new Socket(serverName, socketNumber);
            out = new PrintWriter(connection.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Timer timer = new Timer();
            TimerTask updateOutputMessage = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(in.ready()) {
                            String line = in.readLine();
                            if (line != null && !line.equals("")) {
                                outputMessage.append("\n").append(line);
                                System.out.println(line);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errorMessage = e.getMessage();
                        connectionState = State.ERROR;
                    }
                }
            };
            timer.schedule(updateOutputMessage,250L, 250L);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            connectionState = State.ERROR;
        }
        if(connection != null && connection.isConnected()){
            connectionState = State.CONNECTED;
            errorMessage = null;
        }
    }

    public String getOutput(){
        return outputMessage.toString();
    }

    public void appendToOutput(String message){
        outputMessage.append("\n").append(message);
    }

    public void sendMessage(String message){
        outputMessage.append("\n").append(message);
        out.println(message);
    }

    public boolean isAlive(){
        return connection != null && connection.isConnected();
    }

    public void disconnect(){
        if(connection != null && connection.isConnected()){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
                connectionState = State.ERROR;
            }
        }
    }

    public static void main(String[] args){
        TerminalConnection terminalConnection = new TerminalConnection("192.168.1.117", 30004);
        terminalConnection.connect();
        while (terminalConnection.connectionState == State.CONNECTED){
            System.out.print("");
        }
    }

}
