package com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa;

import com.bryandev.user_service.domain.model.User;
import com.bryandev.user_service.domain.port.out.UserRepositoryPort;
import com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa.mapper.UserMapper;
import com.bryandev.user_service.infrastructure.adapter.out.persistence.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepositoryPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        // 1. Mapear de Dominio a Entidad JPA
        var entity = userMapper.toEntity(user);
        // 2. Guardar en la base de datos
        var savedEntity = userRepository.save(entity);
        // 3. Mapear de vuelta a Dominio y retornar
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
