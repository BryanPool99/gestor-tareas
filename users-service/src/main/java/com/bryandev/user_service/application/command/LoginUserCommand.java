package com.bryandev.user_service.application.command;

public record LoginUserCommand(
        String username,
        String password
) {
}
