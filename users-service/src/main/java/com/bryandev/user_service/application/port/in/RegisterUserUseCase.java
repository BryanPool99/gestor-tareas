package com.bryandev.user_service.application.port.in;

import com.bryandev.user_service.application.command.RegisterUserCommand;
import com.bryandev.user_service.domain.model.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);
}
