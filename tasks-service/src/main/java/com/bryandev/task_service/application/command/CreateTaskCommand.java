package com.bryandev.task_service.application.command;

public record CreateTaskCommand(
        String title,
        String description,
        Long userId
) {
}
