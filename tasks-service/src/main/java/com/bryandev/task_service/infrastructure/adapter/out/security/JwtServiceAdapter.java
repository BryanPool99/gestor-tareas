package com.bryandev.task_service.infrastructure.adapter.out.security;

import com.bryandev.task_service.domain.port.out.JwtServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtServiceAdapter implements JwtServicePort {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Long extractUserId(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = getClaims(token);
            String extractedUsername = claims.getSubject();
            Date expiration = claims.getExpiration();
            return extractedUsername.equals(username) && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
