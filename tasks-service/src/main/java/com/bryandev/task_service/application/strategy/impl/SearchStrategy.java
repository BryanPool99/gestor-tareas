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
public class SearchStrategy implements TaskListStrategy {
    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public boolean supports(ListTasksQuery query) {
        return query.search() != null && !query.search().isBlank();
    }

    @Override
    public PagedResult<Task> execute(ListTasksQuery query) {
        return taskRepositoryPort.searchByTitleOrDescription(query.search(), query.pagination());
    }
}
