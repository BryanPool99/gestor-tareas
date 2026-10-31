package com.bryandev.task_service.application.strategy;

import com.bryandev.task_service.application.query.ListTasksQuery;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskListStrategyContext {
    private final List<TaskListStrategy> strategies;

    public PagedResult<Task> execute(ListTasksQuery query) {
        return strategies.stream()
                .filter(s -> s.supports(query))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No strategy found"))
                .execute(query);
    }
}
