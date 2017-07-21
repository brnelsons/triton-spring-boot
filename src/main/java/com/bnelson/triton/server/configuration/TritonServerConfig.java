package com.bnelson.triton.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by brnel on 7/20/2017.
 */
@Component
@ConfigurationProperties(prefix="triton")
public class TritonServerConfig {

    private String configLocation = null;

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}
