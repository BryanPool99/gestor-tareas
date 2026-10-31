package com.bryandev.task_service.application.usecase;

import com.bryandev.task_service.application.query.GetTaskByIdQuery;
import com.bryandev.task_service.domain.model.Task;

public interface GetTaskByIdUseCase {
    Task execute(GetTaskByIdQuery query);
}
