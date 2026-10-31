package com.bryandev.user_service.application.query;

import com.bryandev.user_service.domain.model.User;
import com.bryandev.user_service.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetUserByUsernameQuery {
    private final UserRepositoryPort userRepositoryPort;

    public Optional<User> execue(String username) {
        return userRepositoryPort.findByUsername(username);
    }
}
