package com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa.repository;

import com.bryandev.task_service.infrastructure.adapter.out.persistence.jpa.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findByUserId(Long userId, Pageable pageable);

    Page<TaskEntity> findByUserIdAndStatus(Long userId, String status, Pageable pageable);

    @Query("""
            SELECT t FROM TaskEntity t WHERE
            LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<TaskEntity> searchByTitleOrDescription(@Param("keyword") String keyword, Pageable pageable);

    void deleteAllByIdIn(List<Long> ids);
}
