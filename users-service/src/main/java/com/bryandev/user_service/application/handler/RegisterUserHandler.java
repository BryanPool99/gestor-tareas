package com.bryandev.user_service.application.handler;

import com.bryandev.user_service.application.command.RegisterUserCommand;
import com.bryandev.user_service.application.port.in.RegisterUserUseCase;
import com.bryandev.user_service.domain.exception.UsernameAlreadyExistsException;
import com.bryandev.user_service.domain.model.User;
import com.bryandev.user_service.domain.port.out.PasswordHasherPort;
import com.bryandev.user_service.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegisterUserHandler implements RegisterUserUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordHasherPort passwordHasherPort;

    @Override
    @Transactional
    public User register(RegisterUserCommand command) {
        // 1. Regla de negocio: Validar que el usuario no exista
        if (userRepositoryPort.existsByUsername(command.username())) {
            throw new UsernameAlreadyExistsException(command.username());
        }
        // 2. Usar el puerto de salida para encriptar (no usamos BCrypt directamente aquí)
        String hashedPassword = passwordHasherPort.hash(command.rawPassword());
        // 3. Crear la entidad de dominio
        User newUser = User.create(command.username(), command.email(), hashedPassword);
        // 4. Guardar usando el puerto de salida
        return userRepositoryPort.save(newUser);
    }
}
