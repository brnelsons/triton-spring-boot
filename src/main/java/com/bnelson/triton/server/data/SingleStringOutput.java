package com.bnelson.triton.server.data;

/**
 * Created by brnel on 7/31/2017.
 */
public class SingleStringOutput implements OutputDelegate.Output {

    private final String s;
    private boolean hasNext = true;

    public SingleStringOutput(String s) {
        this.s = s;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public String next() {
        hasNext = false;
        return s;
    }
}
