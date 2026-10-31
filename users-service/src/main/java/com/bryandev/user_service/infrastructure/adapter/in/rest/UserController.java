package com.bryandev.user_service.infrastructure.adapter.in.rest;

import com.bryandev.user_service.application.command.RegisterUserCommand;
import com.bryandev.user_service.application.port.in.RegisterUserUseCase;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.request.CreateUserRequest;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.response.ApiResponse;
import com.bryandev.user_service.infrastructure.adapter.in.rest.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;

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
}
