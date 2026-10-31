package com.bryandev.task_service.application.command;

import java.util.List;

public record DeleteTaskCommand(
        List<Long> ids
) {
}
