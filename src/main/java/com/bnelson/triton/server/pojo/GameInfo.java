package com.bnelson.triton.server.pojo;


import com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * Created by brnel on 7/20/2017.
 */
public class GameInfo implements Serializable{
    private final String name;
    private final String description;

    public GameInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private String description;

        private Builder(){}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public GameInfo build(){
            Preconditions.checkNotNull(name, "game name cannot be null!");
            return new GameInfo(name, description);
        }
    }
}
