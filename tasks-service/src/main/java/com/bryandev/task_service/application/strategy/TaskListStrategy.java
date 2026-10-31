package com.bryandev.task_service.application.strategy;

import com.bryandev.task_service.application.query.ListTasksQuery;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;

public interface TaskListStrategy {
    boolean supports(ListTasksQuery query);

    PagedResult<Task> execute(ListTasksQuery query);
}
