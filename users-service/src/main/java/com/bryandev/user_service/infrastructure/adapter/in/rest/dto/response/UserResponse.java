package com.bryandev.user_service.infrastructure.adapter.in.rest.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
