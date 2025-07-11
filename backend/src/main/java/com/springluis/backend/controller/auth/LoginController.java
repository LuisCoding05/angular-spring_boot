package com.springluis.backend.controller.auth;

import com.springluis.backend.model.dto.LoginRequest;
import com.springluis.backend.model.dto.LoginResponse;
import com.springluis.backend.services.implement.CustomUserDetailsService;
import com.springluis.backend.services.service.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getIdentificador(), request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getIdentificador());
        final String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }
}
