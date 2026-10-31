package com.bryandev.task_service.infrastructure.adapter.in.rest.security;

import com.bryandev.task_service.domain.exception.InvalidTokenException;
import com.bryandev.task_service.domain.port.out.JwtServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServicePort jwtServicePort;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader==null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // 1. Extraer username
            String username = jwtServicePort.extractUsername(token);

            // 2. Validar que el token sea válido para ese usuario
            if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

                if (jwtServicePort.isTokenValid(token, username)) {

                    UserDetails userDetails = User.builder()
                            .username(username)
                            .password("N/A") // No importa, no usamos auth por contraseña aquí
                            .authorities(new ArrayList<>())
                            .build();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    // El token no es válido (expiró o fue manipulado)
                    throw new InvalidTokenException("El token ha expirado o no es válido");
                }
            }
        } catch (InvalidTokenException e) {
            // Relanzamos nuestra excepción personalizada para que la capture el GlobalExceptionHandler
            throw e;
        } catch (Exception e) {
            // Capturamos cualquier otro error de JWT (ej: token mal formado, firma incorrecta)
            throw new InvalidTokenException("Error al procesar el token de autenticación");
        }

        filterChain.doFilter(request, response);
    }
}