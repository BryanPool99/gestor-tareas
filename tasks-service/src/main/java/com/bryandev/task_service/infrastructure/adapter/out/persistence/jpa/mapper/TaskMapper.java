package com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa.mapper;

import com.bryandev.task_service.domain.criteria.PaginationParams;
import com.bryandev.task_service.domain.model.PagedResult;
import com.bryandev.task_service.domain.model.Task;
import com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public PageRequest toPageable(PaginationParams params) {
        Sort.Direction direction = Sort.Direction.fromString(params.sortDirection());
        return PageRequest.of(params.page(), params.size(), Sort.by(direction, params.sortBy()));
    }

    public TaskEntity toEntity(Task domain) {
        if (domain==null) return null;
        return TaskEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .userId(domain.getUserId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public Task toDomain(TaskEntity entity) {
        if (entity==null) return null;
        return Task.restore(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public <T> PagedResult<T> toPagedResult(Page<T> page) {
        return new PagedResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
