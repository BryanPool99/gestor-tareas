package com.bryandev.task_service.application.dto.response;

import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;

import java.util.List;

public record TaskPageResponse(
        List<TaskResponse> content, int page, int size,
        long totalElements, int totalPages, boolean isLast
) {
    public static TaskPageResponse from(PagedResult<Task> pagedResult) {
        List<TaskResponse> content = pagedResult.content().stream()
                .map(TaskResponse::from).toList();

        return new TaskPageResponse(
                content, pagedResult.page(), pagedResult.size(),
                pagedResult.totalElements(), pagedResult.totalPages(), pagedResult.isLast()
        );
    }
}
