package com.bnelson.triton.server.script;

import com.bnelson.triton.server.data.OutputDelegate;
import com.bnelson.triton.server.data.OutputReader;
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

    private BufferedReader input;

    public BatchScriptRunner(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        ProcessBuilder pb = ScriptUtil.getProcessBuilder(command);
        pb.redirectInput();
        pb.redirectOutput();
        try {
            Process process = pb.start();
            input = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );
        } catch (IOException e) {
            LOGGER.error("something bad happened while running the script {}" ,command, e);
        }
    }

    public OutputDelegate.Output getOutput(){
        return new OutputReader(input);
    }


}
