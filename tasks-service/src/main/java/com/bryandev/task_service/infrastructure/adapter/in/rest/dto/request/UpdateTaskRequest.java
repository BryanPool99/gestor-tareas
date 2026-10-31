package com.bryandev.task_service.infrastructure.adapter.in.rest.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @Size(max = 100, message = "El título no puede exceder 100 caracteres")
        String title,

        @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
        String description
) {
}
