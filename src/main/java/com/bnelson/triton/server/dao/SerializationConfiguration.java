package com.bnelson.triton.server.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by brnel on 6/21/2017.
 */
@Configuration
public class SerializationConfiguration {

    @Bean("gson")
    public Gson gson(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }
}
