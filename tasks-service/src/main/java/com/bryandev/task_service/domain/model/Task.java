package com.bryandev.task_service.domain.model;

import com.bryandev.task_service.domain.enums.TaskStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Task {
    private final Long id;
    private final Long userId;
    private final LocalDateTime createdAt;

    // Campos mutables controlados
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime updatedAt;

    // Constructor privado para forzar el uso del Factory Method
    private Task(
            Long id, String title, String description, TaskStatus status, Long userId, LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory Method: La única forma de crear una tarea
    public static Task create(String title, String description, Long userId) {
        if (title==null || title.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio");
        }
        if (userId==null) {
            throw new IllegalArgumentException("El ID del usuario creador es obligatorio");
        }

        LocalDateTime now = LocalDateTime.now();
        return new Task(null, title, description, TaskStatus.TODO, userId, now, now);
    }

    public static Task restore(
            Long id, String title, String description, TaskStatus status, Long userId, LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Task(id, title, description, status, userId, createdAt, updatedAt);
    }

    // Métodos de comportamiento (Reglas de negocio)
    public void updateDetails(String newTitle, String newDescription) {
        if (this.status==TaskStatus.DONE) {
            throw new IllegalStateException("No se puede editar una tarea completada");
        }
        if (newTitle!=null && !newTitle.isBlank()) this.title = newTitle;
        if (newDescription!=null) this.description = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(TaskStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    // Método para actualizar solo lo que viene con datos
    public void updatePartial(String newTitle, String newDescription, TaskStatus newStatus) {
        if (newTitle!=null) {
            if (newTitle.isBlank()) {
                throw new IllegalArgumentException("El título no puede estar vacío");
            }
            this.title = newTitle;
        }

        if (newDescription!=null) {
            this.description = newDescription;
        }

        if (newStatus!=null) {
            this.status = newStatus;
        }
    }
}
