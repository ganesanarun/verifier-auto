package com.example.autoverifier;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "headers")
@Data
public class APIKeys {

    private Map<String, String> legacy;
    private Map<String, String> latest;
}
