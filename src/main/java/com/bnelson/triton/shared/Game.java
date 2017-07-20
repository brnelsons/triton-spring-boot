package com.bnelson.triton.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Created by brnel on 6/14/2017.
 */
public class Game implements Serializable, IsSerializable{
    private String name;

    public Game(String name) {
        this.name = name;
    }

    //default for gwt support
    public Game() {}

    public String getName() {
        return name;
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
