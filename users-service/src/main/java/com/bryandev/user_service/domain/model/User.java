package com.bryandev.user_service.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private final Long id;
    private final String username;
    private final String email;
    private final String passwordHash; // Nunca guardamos el password en texto plano
    private final LocalDateTime createdAt;

    // Constructor privado para forzar el uso del factory method
    private User(Long id, String username, String email, String passwordHash, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    // Factory Method: La forma correcta de crear un usuario
    public static User create(String username, String email, String passwordHash) {
        // Aquí podrías agregar validaciones de dominio (ej: username no vacío)
        if (username==null || username.isBlank()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        return new User(null, username, email, passwordHash, LocalDateTime.now());
    }

    // Para RECONSTRUIR desde la Base de Datos (en el Mapper)
    public static User restore(Long id, String username, String email, String passwordHash, LocalDateTime createdAt) {
        return new User(id, username, email, passwordHash, createdAt);
    }
}
