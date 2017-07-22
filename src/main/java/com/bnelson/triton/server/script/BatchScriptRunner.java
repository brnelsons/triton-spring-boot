package com.bnelson.triton.server.script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by brnel on 7/21/2017.
 */
public class BatchScriptRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchScriptRunner.class);

    private final String command;

    private final StringBuilder output;

    public BatchScriptRunner(String command) {
        this.command = command;
        output = new StringBuilder();
    }

    @Override
    public void run() {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectInput();
        try {
            Process process = pb.start();
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
            String line;
            while((line = input.readLine()) != null){
                if(!line.isEmpty()){
                    output.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            LOGGER.error("something bad happened while running the script {}" ,command, e);
        }
    }

    public String getOutput(){
        return output.toString();
    }

}
