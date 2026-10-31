package com.bryandev.user_service.application.dto.response;

public record AuthResponse(
        String token,
        String username,
        String message
) {
}
