package com.bryandev.user_service.infrastructure.config;

import com.bryandev.user_service.infrastructure.adapter.out.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (necesario para APIs REST stateless / JWT)
                .csrf(AbstractHttpConfigurer::disable)
                // 2. No usar sesiones HTTP (preparado para JWT)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 3. Definir qué rutas son públicas y cuáles privadas
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/v1/users/register").permitAll()//cualquiera puede registrarse
                                .requestMatchers("/api/v1/users/login").permitAll()//cualquiera puede intentar loguearse
                                .requestMatchers("/error").permitAll()//Permitir manejo de errores
                                .anyRequest().authenticated()//Lo demás requiere autenticación
                )
                // Agregar el filtro JWT ANTES del filtro de autenticación de Spring
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Este es el bean que tu BcryptPasswordHasherAdapter inyectará automáticamente
        return new BCryptPasswordEncoder();
    }
}
