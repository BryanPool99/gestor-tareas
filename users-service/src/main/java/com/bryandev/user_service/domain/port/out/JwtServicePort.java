package com.bryandev.user_service.domain.port.out;

public interface JwtServicePort {
    String generateToken(String username);
    boolean validateToken(String token);
    String extractUsername(String token);
}
