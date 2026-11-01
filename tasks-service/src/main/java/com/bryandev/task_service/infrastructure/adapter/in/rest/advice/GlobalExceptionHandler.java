package com.bryandev.task_service.infrastructure.adapter.in.rest.advice;

import com.bryandev.task_service.domain.exception.InvalidTokenException;
import com.bryandev.task_service.domain.exception.TaskNotFoundException;
import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTaskNotFound(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        "Recurso no encontrado",
                        HttpStatus.NOT_FOUND.value(),
                        "TASK_NOT_FOUND",
                        ex.getMessage())
                );
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidToken(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(
                        "Error de autenticación",
                        HttpStatus.UNAUTHORIZED.value(),
                        "INVALID_TOKEN",
                        ex.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        "Error interno del servidor",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "INTERNAL_SERVER_ERROR",
                        "Ocurrió un error inesperado")
                );
    }
}