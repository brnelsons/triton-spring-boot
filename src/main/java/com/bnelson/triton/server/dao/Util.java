package com.bnelson.triton.server.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by brnel on 6/21/2017.
 */
public class Util {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
}
