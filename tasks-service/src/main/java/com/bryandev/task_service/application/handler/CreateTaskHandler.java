package com.bryandev.task_service.application.handler;

import com.bryandev.task_service.application.command.CreateTaskCommand;
import com.bryandev.task_service.application.usecase.CreateTaskUseCase;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateTaskHandler implements CreateTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    @Transactional
    public Task execute(CreateTaskCommand command) {
        var newTask = Task.create(command.title(), command.description(), command.userId());
        return taskRepositoryPort.save(newTask);
    }
}
