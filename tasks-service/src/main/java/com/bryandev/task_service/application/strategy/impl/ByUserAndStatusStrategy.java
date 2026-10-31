package com.bryandev.task_service.application.strategy.impl;

import com.bryandev.task_service.application.query.ListTasksQuery;
import com.bryandev.task_service.application.strategy.TaskListStrategy;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ByUserAndStatusStrategy implements TaskListStrategy {

    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public boolean supports(ListTasksQuery query) {
        return query.userId()!=null && query.status()!=null;
    }

    @Override
    public PagedResult<Task> execute(ListTasksQuery query) {
        return taskRepositoryPort.findByUserIdAndStatus(query.userId(), query.status(), query.pagination());
    }
}
