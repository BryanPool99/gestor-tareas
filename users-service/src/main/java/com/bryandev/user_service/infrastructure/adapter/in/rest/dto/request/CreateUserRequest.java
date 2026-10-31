package com.bryandev.user_service.infrastructure.adapter.in.rest.dto.request;

public record CreateUserRequest(
        String username, String email, String password
) {
}
