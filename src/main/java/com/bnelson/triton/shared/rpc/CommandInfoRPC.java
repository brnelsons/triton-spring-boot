package com.bnelson.triton.shared.rpc;

import java.io.Serializable;

public class CommandInfoRPC implements Serializable{

    public enum Name{
        START, UPDATE, STOP, KILL, OTHER
    }

    private Name name;
    private String description;

    /**
     * Explicit for GWT serialization
     */
    public CommandInfoRPC() {}

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
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
