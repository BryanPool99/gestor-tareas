package com.bryandev.task_service.application.usecase;

import com.bryandev.task_service.application.query.ListTasksQuery;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;

import java.util.List;

public interface ListTasksUseCase {
    PagedResult<Task> execute(ListTasksQuery query);
}
