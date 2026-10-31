package com.bryandev.task_service.application.usecase;

import com.bryandev.task_service.application.command.DeleteTaskCommand;

public interface DeleteTaskUseCase {
    void execute(DeleteTaskCommand command);
}
