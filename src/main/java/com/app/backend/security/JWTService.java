package com.app.backend.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JWTService {
    public String generateToken(UUID userId);
    public boolean validateToken(String token);
    public UUID extractUserId(String token);
}
