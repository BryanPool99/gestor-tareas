package com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa;

import com.bryandev.task_service.domain.criteria.PaginationParams;
import com.bryandev.task_service.domain.enums.TaskStatus;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.domain.port.out.TaskRepositoryPort;
import com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa.mapper.TaskMapper;
import com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa.repository.JpaTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryAdapter implements TaskRepositoryPort {
    private final JpaTaskRepository jpaRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id).map(taskMapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        jpaRepository.deleteAllById(ids);
    }

    @Override
    public PagedResult<Task> findAll(PaginationParams pagination) {
        var page = jpaRepository.findAll(taskMapper.toPageable(pagination));
        var tasks = page.getContent().stream()
                .map(taskMapper::toDomain)
                .toList();

        return new PagedResult<>(
                tasks, page.getNumber(), page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isLast()
        );
    }

    @Override
    public PagedResult<Task> findByUserId(Long userId, PaginationParams pagination) {
        var page = jpaRepository.findByUserId(userId, taskMapper.toPageable(pagination));
        var tasks = page.getContent().stream().map(taskMapper::toDomain).toList();

        return new PagedResult<>(tasks, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public PagedResult<Task> findByUserIdAndStatus(Long userId, TaskStatus status, PaginationParams pagination) {
        var page = jpaRepository.findByUserIdAndStatus(userId, status.name(), taskMapper.toPageable(pagination));
        List<Task> tasks = page.getContent().stream().map(taskMapper::toDomain).toList();

        return new PagedResult<>(tasks, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    public PagedResult<Task> searchByTitleOrDescription(String keyword, PaginationParams pagination) {
        var page = jpaRepository.searchByTitleOrDescription(keyword, taskMapper.toPageable(pagination));
        var tasks = page.getContent().stream().map(taskMapper::toDomain).toList();

        return new PagedResult<>(tasks, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isLast());
    }
}
