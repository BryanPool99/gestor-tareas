package com.bryandev.user_service.infrastructure.adapter.out.security;

import com.bryandev.user_service.domain.port.out.JwtServicePort;
import com.bryandev.user_service.domain.port.out.UserRepositoryPort;
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
    private final UserRepositoryPort userRepositoryPort;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extraer el token del header Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No hay token, continuar sin autenticación
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token (quitar "Bearer ")
        String token = authHeader.substring(7);

        try {
            // 3. Extraer el username del token
            String username = jwtServicePort.extractUsername(token);

            // 4. Si hay username y no hay autenticación previa en el contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 5. Validar el token
                if (jwtServicePort.validateToken(token, username)) {

                    // 6. Cargar el usuario de la base de datos
                    userRepositoryPort.findByUsername(username).ifPresent(user -> {

                        // 7. Crear UserDetails (Spring Security necesita esto)
                        UserDetails userDetails = User.builder()
                                .username(user.getUsername())
                                .password(user.getPasswordHash())
                                .authorities(new ArrayList<>()) // Sin roles por ahora
                                .build();

                        // 8. Crear el objeto de autenticación
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // 9. Guardar en el contexto de seguridad
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    });
                }
            }
        } catch (Exception e) {
            // Token inválido o expirado - no autenticar
            SecurityContextHolder.clearContext();
        }

        // 10. Continuar con el filtro
        filterChain.doFilter(request, response);
    }
}