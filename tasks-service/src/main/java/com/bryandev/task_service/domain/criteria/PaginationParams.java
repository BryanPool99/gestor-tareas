package com.bryandev.task_service.domain.criteria;

public record PaginationParams(
        int page,
        int size,
        String sortBy,
        String sortDirection
) {
    // Valores por defecto si no se envían
    public PaginationParams {
        if (page < 0) page = 0;
        if (size < 1 || size > 100) size = 10;
        if (sortBy==null || sortBy.isBlank()) sortBy = "createdAt";
        if (sortDirection==null || sortDirection.isBlank()) sortDirection = "DESC";
    }
}
