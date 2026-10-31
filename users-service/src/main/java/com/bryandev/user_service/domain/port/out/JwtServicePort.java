package com.bryandev.user_service.domain.port.out;

public interface JwtServicePort {
    String generateToken(String username);

    String extractUsername(String token);

    boolean validateToken(String token, String username);
}
