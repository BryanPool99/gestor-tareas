package com.bryandev.task_service.domain.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("La tarea con ID " + id + " no fue encontrada");
    }
}
