package com.bnelson.triton.server.data;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by brnel on 7/31/2017.
 */
public class OutputReader implements OutputDelegate.Output {
    private final BufferedReader reader;
    private String line;

    public OutputReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        try {
            return (line = reader.readLine()) != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String next() {
        return line;
    }
}
