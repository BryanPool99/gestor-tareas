package com.bryandev.task_service.application.usecase;

import com.bryandev.task_service.application.command.PatchTaskCommand;
import com.bryandev.task_service.domain.model.Task;

public interface PatchTaskUseCase {
    Task execute(PatchTaskCommand command);
}
