package com.bnelson.triton.shared.rpc;

import java.io.Serializable;

/**
 * Created by brnel on 7/20/2017.
 */
public class ServerInfoRPC implements Serializable {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerInfoRPC that = (ServerInfoRPC) o;

        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        return address != null ? address.hashCode() : 0;
    }
}
