package com.springluis.backend.services.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    /**
     * Extrae el nombre de usuario (subject) de un token JWT.
     */
    String extractUsername(String token);

    /**
     * Genera un token JWT para un usuario.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Valida si un token JWT es válido para un usuario específico.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
