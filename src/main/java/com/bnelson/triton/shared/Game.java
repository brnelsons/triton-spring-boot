package com.bnelson.triton.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by brnel on 6/14/2017.
 */
public class Game implements Serializable{
    private String name;

    @JsonCreator
    public Game(@JsonProperty("name") String name) {
        this.name = name;
    }

    //default for gwt support
    public Game() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return name != null ? name.equals(game.name) : game.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
