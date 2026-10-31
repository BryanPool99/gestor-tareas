package com.bryandev.task_service.domain.port.out;

import com.bryandev.task_service.domain.criteria.PaginationParams;
import com.bryandev.task_service.domain.enums.TaskStatus;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepositoryPort {
    Task save(Task task);

    Optional<Task> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    void deleteAllById(List<Long> ids);

    // Consultas paginadas
    PagedResult<Task> findAll(PaginationParams pagination);

    PagedResult<Task> findByUserId(Long userId, PaginationParams pagination);

    PagedResult<Task> findByUserIdAndStatus(Long userId, TaskStatus status, PaginationParams pagination);

    PagedResult<Task> searchByTitleOrDescription(String keyword, PaginationParams pagination);

}
