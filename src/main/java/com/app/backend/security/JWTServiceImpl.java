package com.app.backend.security;

import com.app.backend.config.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

        return Jwts.builder().setSubject(userId.toString())
                .setIssuedAt(now).setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(signingKey)
                    .build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    @Override
    public UUID extractUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey)
                .build().parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}
