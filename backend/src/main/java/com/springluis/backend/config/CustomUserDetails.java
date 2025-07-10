package com.springluis.backend.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springluis.backend.model.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    
    private final User user;
    
    public CustomUserDetails(User user) {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte tus roles a GrantedAuthority
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getPassword() {
        return user.getKey();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
    // @Override
    // public boolean isAccountNonExpired() {
    //     return user.();
    // }
    
    // @Override
    // public boolean isAccountNonLocked() {
    //     return user.isAccountNonLocked();
    // }
    
    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return user.isCredentialsNonExpired();
    // }
    
    // @Override
    // public boolean isEnabled() {
    //     return user.isEnabled();
    // }
    
    // Método adicional para acceder a tu entidad User completa
    public User getUser() {
        return user;
    }
}