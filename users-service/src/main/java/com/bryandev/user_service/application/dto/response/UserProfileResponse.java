package com.bryandev.user_service.application.dto.response;

import java.time.LocalDateTime;

public record UserProfileResponse(
        Long id, String username, String email, LocalDateTime createdAt
) {
}
