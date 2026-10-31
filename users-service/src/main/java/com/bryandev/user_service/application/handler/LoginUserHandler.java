package com.bryandev.user_service.application.handler;

import com.bryandev.user_service.application.command.LoginUserCommand;
import com.bryandev.user_service.application.dto.response.AuthResponse;
import com.bryandev.user_service.application.port.in.LoginUserUseCase;
import com.bryandev.user_service.domain.exception.InvalidCredentialsException;
import com.bryandev.user_service.domain.model.User;
import com.bryandev.user_service.domain.port.out.JwtServicePort;
import com.bryandev.user_service.domain.port.out.PasswordHasherPort;
import com.bryandev.user_service.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserHandler implements LoginUserUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordHasherPort passwordHasherPort;
    private final JwtServicePort jwtServicePort;

    @Override
    public AuthResponse login(LoginUserCommand command) {
        // 1. Buscar usuario por username
        User user = userRepositoryPort.findByUsername(command.username())
                .orElseThrow(InvalidCredentialsException::new);

        // 2. Validar contraseña
        boolean isPasswordValid = passwordHasherPort.matches(command.password(), user.getPasswordHash());
        if (!isPasswordValid) {
            throw new InvalidCredentialsException();
        }

        // 3. Generar Token JWT
        String token = jwtServicePort.generateToken(user.getUsername());

        // 4. Retornar respuesta
        return new AuthResponse(token, user.getUsername(), "Login exitoso");
    }
}
