package com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa.repository;

import com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
