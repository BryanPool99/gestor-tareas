package com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa.mapper;

import com.bryandev.user_service.domain.model.User;
import com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserEntity entity) {
        if (entity==null) return null;
        return User.restore(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getCreatedAt()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain==null) return null;
        return UserEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .email(domain.getEmail())
                .passwordHash(domain.getPasswordHash())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
