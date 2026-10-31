package com.bryandev.task_service.application.usecase;

import com.bryandev.task_service.application.command.CreateTaskCommand;
import com.bryandev.task_service.domain.model.Task;

public interface CreateTaskUseCase {
    Task execute(CreateTaskCommand command);
}
