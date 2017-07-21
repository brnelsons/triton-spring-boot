package com.bnelson.triton.server.pojo;


import com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * Created by brnel on 7/20/2017.
 */
public class GameInfo implements Serializable{
    private final String id;
    private final String name;
    private final String description;

    public GameInfo(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
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

    public static Builder newBuilder(GameInfo from){
        return new Builder(from);
    }

    public static class Builder{

        private String id;
        private String name;
        private String description;

        private Builder(){}
        private Builder(GameInfo from){
            this.id = from.getId();
            this.name = from.getName();
            this.description = from.getDescription();
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

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
            Preconditions.checkNotNull(id, "id name cannot be null!");
            return new GameInfo(id, name, description);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfo gameInfo = (GameInfo) o;

        if (name != null ? !name.equals(gameInfo.name) : gameInfo.name != null) return false;
        return description != null ? description.equals(gameInfo.description) : gameInfo.description == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
