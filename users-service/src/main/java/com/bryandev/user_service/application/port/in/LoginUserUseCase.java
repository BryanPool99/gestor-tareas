package com.bryandev.user_service.application.port.in;

import com.bryandev.user_service.application.command.LoginUserCommand;
import com.bryandev.user_service.application.dto.response.AuthResponse;

public interface LoginUserUseCase {
    AuthResponse login(LoginUserCommand command);
}
