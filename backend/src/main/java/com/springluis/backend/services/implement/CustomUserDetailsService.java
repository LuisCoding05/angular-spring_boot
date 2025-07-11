package com.springluis.backend.services.implement;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springluis.backend.config.CustomUserDetails;
import com.springluis.backend.model.entity.User;
import com.springluis.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // El 'username' que recibimos aquí puede ser un email o un nombre de usuario.
        log.debug("Buscando usuario por identificador: {}", username);

        // Buscar por email o por username usando los nuevos métodos del repositorio
        User user = userRepository.findByEmailWithRoles(username)
                .orElseGet(() -> userRepository.findByUsernameWithRoles(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el identificador: " + username)));
        
        log.info("Usuario encontrado: {}", user.getUsername());
        return new CustomUserDetails(user);
    }

}