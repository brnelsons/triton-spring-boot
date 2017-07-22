package com.bnelson.triton.server.pojo;


import com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * Created by brnel on 7/20/2017.
 */
public class Command implements Serializable {

    public enum Type{
        LOCAL_SCRIPT,
        TELNET
    }

    private final String name;
    private final Type type;
    private final String description;
    private final String value;

    public Command(String name, Type type, String description, String value) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private Type type;
        private String description;
        private String value;

        private Builder(){}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setType(Type type) {
            this.type = type;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Command build(){
            Preconditions.checkNotNull(name, "command name cannot be null!");
            Preconditions.checkNotNull(type, "command type cannot be null!");
            Preconditions.checkNotNull(value, "command value cannot be null!");
            return new Command(name, type, description, value);
        }
    }
}
