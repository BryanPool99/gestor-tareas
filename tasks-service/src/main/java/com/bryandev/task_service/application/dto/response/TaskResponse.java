package com.bryandev.task_service.application.dto.response;

import com.bryandev.task_service.domain.enums.TaskStatus;
import com.bryandev.task_service.domain.model.Task;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUserId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
