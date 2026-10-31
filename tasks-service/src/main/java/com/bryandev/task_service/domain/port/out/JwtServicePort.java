package com.bryandev.task_service.domain.port.out;

public interface JwtServicePort {
    String extractUsername(String token);

    boolean isTokenValid(String token, String username);
}
