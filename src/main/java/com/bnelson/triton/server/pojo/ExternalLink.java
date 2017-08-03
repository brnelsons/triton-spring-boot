package com.bnelson.triton.server.pojo;

import com.google.common.base.Preconditions;

import java.io.Serializable;

/**
 * Created by brnel on 7/29/2017.
 */
public class ExternalLink implements Serializable{

    private final String name;
    private final String url;

    public ExternalLink(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private String name;
        private String url;

        private Builder(){}

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ExternalLink build(){
            Preconditions.checkNotNull(name, "external link name cannot be null!");
            Preconditions.checkNotNull(url, "external link url cannot be null!");

            return new ExternalLink(name, url);
        }
    }
}
