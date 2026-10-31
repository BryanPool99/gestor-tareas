package com.bryandev.task_service.domain.model;

import java.util.List;

public record PagedResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean isLast
) {
}
