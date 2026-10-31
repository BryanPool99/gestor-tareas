package com.bryandev.task_service.application.usecase;

import com.bryandev.task_service.application.command.UpdateTaskCommand;
import com.bryandev.task_service.domain.model.Task;

public interface UpdateTaskUseCase {
    Task execute(UpdateTaskCommand command);
}
