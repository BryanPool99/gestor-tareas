package com.bryandev.task_service.domain.port.out;

public interface PasswordHasherPort {
    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
