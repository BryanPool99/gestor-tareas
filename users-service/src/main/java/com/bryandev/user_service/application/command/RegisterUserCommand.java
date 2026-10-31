package com.bryandev.user_service.application.command;

public record RegisterUserCommand(
        String username, String email, String rawPassword
) {
}
