package com.bryandev.user_service.application.handler;

import com.bryandev.user_service.application.port.in.GetUserProfileUseCase;
import com.bryandev.user_service.domain.exception.UserNotFoundException;
import com.bryandev.user_service.domain.model.User;
import com.bryandev.user_service.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserProfileHandler implements GetUserProfileUseCase {
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User getProfile(String username) {
        return userRepositoryPort.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
