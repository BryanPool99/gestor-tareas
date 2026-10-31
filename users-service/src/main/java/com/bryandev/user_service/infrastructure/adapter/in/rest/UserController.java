package com.bryandev.user_service.infrastructure.adapter.in.rest;

import com.bryandev.user_service.application.command.LoginUserCommand;
import com.bryandev.user_service.application.command.RegisterUserCommand;
import com.bryandev.user_service.application.dto.response.AuthResponse;
import com.bryandev.user_service.application.dto.response.UserProfileResponse;
import com.bryandev.user_service.application.port.in.GetUserProfileUseCase;
import com.bryandev.user_service.application.port.in.LoginUserUseCase;
import com.bryandev.user_service.application.port.in.RegisterUserUseCase;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.request.CreateUserRequest;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.request.LoginRequest;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.response.ApiResponse;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@RequestBody @Valid CreateUserRequest request) {
        var command = new RegisterUserCommand(
                request.username(), request.email(), request.password()
        );

        var user = registerUserUseCase.register(command);

        var userResponse = new UserResponse(
                user.getId(), user.getUsername(), user.getEmail()
        );

        var apiResponse = ApiResponse.success("Usuario reegistrado exitosamente", userResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
        var command = new LoginUserCommand(
                request.username(),
                request.password()
        );
        var authResponse = loginUserUseCase.login(command);

        var apiResponse = ApiResponse.success("Login exitoso", authResponse);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(Authentication authentication) {

        // 1. Obtener el username del contexto de seguridad (inyectado por el filtro JWT)
        String username = authentication.getName();

        // 2. Ejecutar caso de uso
        var user = getUserProfileUseCase.getProfile(username);

        // 3. Mapear a DTO
        var response = new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );

        // 4. Envolver en ApiResponse
        var apiResponse = ApiResponse.success("Perfil obtenido exitosamente", response);

        return ResponseEntity.ok(apiResponse);
    }
}
