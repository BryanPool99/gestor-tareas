package com.bryandev.user_service.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("El usuario con username '" + username + "' ya existe.");
    }
}
