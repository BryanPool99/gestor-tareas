package com.bryandev.task_service.infrastructure.adapter.in.rest.security;

import com.bryandev.task_service.infrastructure.adapter.in.rest.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Void> errorResponse = ApiResponse.error(
                "Acceso no autorizado",
                HttpStatus.UNAUTHORIZED.value(),
                "UNAUTHORIZED",
                "Debe autenticarse para acceder a este recurso"
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}