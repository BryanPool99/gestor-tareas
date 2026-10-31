package com.bryandev.task_service.infrastructure.adapter.in.rest.advice;

import com.bryandev.task_service.domain.exception.InvalidTokenException;
import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException ex) {
        ApiResponse<Void> response = ApiResponse.error(
                "Error de autenticación",
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_TOKEN",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
