package com.bryandev.task_service.application.handler;

import com.bryandev.task_service.application.command.UpdateTaskCommand;
import com.bryandev.task_service.application.usecase.UpdateTaskUseCase;
import com.bryandev.task_service.domain.exception.TaskNotFoundException;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateTaskHandler implements UpdateTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    @Transactional
    public Task execute(UpdateTaskCommand command) {
        var taskExiting = taskRepositoryPort.findById(command.id())
                .orElseThrow(() -> new TaskNotFoundException(command.id()));
        taskExiting.updateDetails(command.title(), command.description());
        return null;
    }
}
