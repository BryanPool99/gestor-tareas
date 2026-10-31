package com.bryandev.task_service.application.query;

import com.bryandev.task_service.domain.enums.TaskStatus;
import com.bryandev.task_service.domain.criteria.PaginationParams;

public record ListTasksQuery(
        Long userId,
        TaskStatus status,
        String search,        // Búsqueda en título/descripción
        PaginationParams pagination // Paginación y ordenamiento
) {
}
