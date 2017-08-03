package com.bnelson.triton.server.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by brnel on 7/31/2017.
 */
public class OutputDelegate {

    private final StringBuilder output;
    private final Queue<Output> outputQueue;
    private final int batchSize;

    public OutputDelegate(int batchSize){
        this.batchSize = batchSize;
        outputQueue = new LinkedList<>();
        output = new StringBuilder("");
    }

    public void addOutput(Output out){
        outputQueue.offer(out);
    }

    public String read(){
        for(Output o : outputQueue){
            int i = 0;
            while(i++<batchSize && o.hasNext()){
                output.append(o.next()).append("\n");
            }
        }
        return output.toString();
    }

    public interface Output extends Iterator<String>{
        @Override
        boolean hasNext();

        @Override
        String next();
    }

}
