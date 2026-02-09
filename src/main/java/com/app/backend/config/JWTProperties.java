package com.app.backend.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.Key;

@ConfigurationProperties(prefix = "jwt")
@Component
public class JWTProperties {
    private String secret;
    private long expirationMs;


    public String getSecret() {
        return secret;
    }
    public long getExpirationMs() {
        return expirationMs;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public void setExpirationMs(long expirationMs) {
        this.expirationMs = expirationMs;
    }

}
