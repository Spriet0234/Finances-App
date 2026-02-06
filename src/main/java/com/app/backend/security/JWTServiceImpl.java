package com.app.backend.security;

import com.app.backend.config.JWTProperties;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTServiceImpl implements JWTService {

    private final Key signingKey;
    private final long expirationMs;

    public JWTServiceImpl(JWTProperties jwtProperties) {
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.expirationMs = jwtProperties.getExpirationMs();
    }


    @Override
    public String generateToken(UUID userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);
        return "";
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public UUID extractUserId(String token) {
        return null;
    }
}
