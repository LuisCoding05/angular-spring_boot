package com.springluis.backend.services.implement;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;
        try {
            user = userRepository.findByUsername(username); 
        } catch (UsernameNotFoundException e) {
            log.error("Usuario no encontrado: {}", username, e);
            user = null;
        }
        
        return new CustomUserDetails(user);
    }
    
    // Método adicional para obtener el usuario por email (opcional)
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepository.findByEmail(email); 
        } catch (UsernameNotFoundException e) {
            log.error("Usuario no encontrado: {}", email, e);
            user = null;
        }
        return new CustomUserDetails(user);
    }
}