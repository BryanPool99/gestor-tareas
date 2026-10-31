package com.bryandev.task_service.application.handler;

import com.bryandev.task_service.application.query.GetTaskByIdQuery;
import com.bryandev.task_service.application.usecase.GetTaskByIdUseCase;
import com.bryandev.task_service.domain.exception.TaskNotFoundException;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetTaskByIdHandler implements GetTaskByIdUseCase {
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public Task execute(GetTaskByIdQuery query) {
        return taskRepositoryPort.findById(query.id()).orElseThrow(() -> new TaskNotFoundException(query.id()));
    }
}
