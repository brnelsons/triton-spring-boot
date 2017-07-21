package com.bnelson.triton.server.pojo;

import com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * Created by brnel on 7/20/2017.
 */
public class ServerInfo implements Serializable {

    private final String username;
    private final String password;
    private final String address;

    public ServerInfo(String username, String password, String address) {
        this.username = username;
        this.password = password;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private String username;
        private String password;
        private String address;

        private Builder(){}

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ServerInfo build(){
            Preconditions.checkNotNull(address, "server address cannot be null!");
            return new ServerInfo(username, password, address);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerInfo that = (ServerInfo) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
