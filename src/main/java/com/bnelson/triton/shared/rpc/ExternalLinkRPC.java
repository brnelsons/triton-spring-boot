package com.bnelson.triton.shared.rpc;

import java.io.Serializable;

/**
 * Created by brnel on 7/29/2017.
 */
public class ExternalLinkRPC implements Serializable{

    private String name;
    private String url;

    public ExternalLinkRPC(){
        //GWT constructor
    }

    public ExternalLinkRPC(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalLinkRPC externalLinkRPC = (ExternalLinkRPC) o;

        if (name != null ? !name.equals(externalLinkRPC.name) : externalLinkRPC.name != null) return false;
        return url != null ? url.equals(externalLinkRPC.url) : externalLinkRPC.url == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
