package com.bryandev.task_service.application.handler;

import com.bryandev.task_service.application.command.PatchTaskCommand;
import com.bryandev.task_service.application.usecase.PatchTaskUseCase;
import com.bryandev.task_service.domain.exception.TaskNotFoundException;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PatchTaskHandler implements PatchTaskUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    @Transactional
    public Task execute(PatchTaskCommand command) {
        // 1. Buscar la entidad existente
        var task = taskRepositoryPort.findById(command.id())
                .orElseThrow(() -> new TaskNotFoundException(command.id()));

        // 2. Aplicar actualización parcial en el dominio
        task.updatePartial(command.title(), command.description(), command.status());

        // 3. Persistir los cambios
        return taskRepositoryPort.save(task);
    }
}
