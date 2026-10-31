package com.bryandev.task_service.application.command;

import com.bryandev.task_service.domain.enums.TaskStatus;

public record PatchTaskCommand(
        Long id,
        String title,
        String description,
        TaskStatus status
) {
}
