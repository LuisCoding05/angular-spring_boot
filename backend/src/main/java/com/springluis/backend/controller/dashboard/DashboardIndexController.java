package com.springluis.backend.controller.dashboard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.services.implement.UserServiceImp;
import com.springluis.backend.services.service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/dashboard/")
@RequiredArgsConstructor
public class DashboardIndexController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserServiceImp userService;

    @GetMapping("index")
    public ResponseEntity<?> index(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El token de autorización es inválido o no se proporcionó.");
        }

        try {
            final String token = authHeader.substring(7);
            final String username = jwtService.extractUsername(token);
            
            Optional<UserDto> user = userService.findByUsername(username);
            if (!user.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            user.get().setKey(null);
            
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido: " + e.getMessage());
        }
    }
}
