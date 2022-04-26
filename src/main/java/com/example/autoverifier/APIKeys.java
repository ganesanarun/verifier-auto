package com.example.autoverifier;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "apikey")
@Data
public class APIKeys {

    private String legacy;
    private String latest;

    final String LEGACY_KEY = "ApiKey";
    final String LATEST_KEY = "ApiKey";

    public Map<String, String> getLatest() {
        return Map.of(LATEST_KEY, latest);
    }

    public Map<String, String> getLegacy() {
        return Map.of(LEGACY_KEY, legacy);
    }
}
