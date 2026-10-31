package com.bryandev.task_service.infrastructure.adapter.in.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)// Oculta campos nulos (ej: si hay error, no muestra data y viceversa)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        ApiError error,
        String timestamp
) {
    // Método estático para respuestas exitosas
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();
    }

    // Estructura interna para errores detallados
    @Builder
    public record ApiError(
            int status,
            String errorCode,
            String details
    ) {
    }

    // Método estático para respuestas de error
    public static ApiResponse<Void> error(String message, int status, String errorCode, String details) {
        return ApiResponse.<Void>builder()
                .success(false)
                .message(message)
                .error(new ApiError(status, errorCode, details))
                .timestamp(java.time.LocalDateTime.now().toString())
                .build();
    }
}
