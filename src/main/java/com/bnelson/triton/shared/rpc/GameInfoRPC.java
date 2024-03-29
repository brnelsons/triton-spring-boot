package com.bnelson.triton.shared.rpc;

import java.io.Serializable;

/**
 * Created by brnel on 7/20/2017.
 */
public class GameInfoRPC implements Serializable{
    private String id;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
