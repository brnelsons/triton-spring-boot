package com.bnelson.triton.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by brnel on 7/19/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "spring-boot-gwt")
public class TritonBootProperties {
}
