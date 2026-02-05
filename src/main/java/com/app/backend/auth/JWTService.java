package com.app.backend.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JWTService {

    private final Key signingKey;
    private final long expirationMs;


    private String generateToken(UserDetails userDetails) {

    }
    private boolean validateToken(String token){

    }
    private String extractUserId(String token){

    }

}
