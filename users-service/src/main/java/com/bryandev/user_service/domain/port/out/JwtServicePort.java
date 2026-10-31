package com.bryandev.user_service.domain.port.out;

public interface JwtServicePort {
    String generateToken(String username, Long userId);

    String extractUsername(String token);

    Long extractUserId(String token);

    boolean validateToken(String token, String username);
}
