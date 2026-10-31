package com.bryandev.user_service.application.port.in;

import com.bryandev.user_service.domain.model.User;

public interface GetUserProfileUseCase {
    User getProfile(String username);
}
