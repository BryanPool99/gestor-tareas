package com.bryandev.task_service.application.handler;

import com.bryandev.task_service.application.command.DeleteTaskCommand;
import com.bryandev.task_service.application.usecase.DeleteTaskUseCase;
import com.bryandev.task_service.domain.exception.TaskNotFoundException;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteTaskHandler implements DeleteTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    @Transactional
    public void execute(DeleteTaskCommand command) {
        command.ids().forEach(
                id -> {
                    if (!taskRepositoryPort.existsById(id)) throw new TaskNotFoundException(id);
                }
        );
        taskRepositoryPort.deleteAllById(command.ids());
    }
}
