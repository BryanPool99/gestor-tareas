package com.bryandev.task_service.application.handler;

import com.bryandev.task_service.application.query.ListTasksQuery;
import com.bryandev.task_service.application.strategy.TaskListStrategyContext;
import com.bryandev.task_service.application.usecase.ListTasksUseCase;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListTasksHandler implements ListTasksUseCase {
    private final TaskListStrategyContext taskListStrategyContext;

    @Override
    public PagedResult<Task> execute(ListTasksQuery query) {
        return taskListStrategyContext.execute(query);
    }
}
