package com.springluis.backend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encriptar {

    public static String encriptarPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}
