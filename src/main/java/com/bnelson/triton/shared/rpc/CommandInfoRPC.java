package com.bnelson.triton.shared.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by bnelson on 7/21/2017.
 */
@SuppressWarnings("GwtInconsistentSerializableClass")
public class CommandInfoRPC implements Serializable{

    private String name;
    private String description;

    /**
     * Explicit for GWT serialization
     */
    public CommandInfoRPC() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommandInfoRPC infoRPC = (CommandInfoRPC) o;

        if (name != null ? !name.equals(infoRPC.name) : infoRPC.name != null) {
            return false;
        }
        return description != null ? description.equals(infoRPC.description) : infoRPC.description == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
