package com.springluis.backend.controller.auth;

import com.springluis.backend.config.constant.AppConstants;
import com.springluis.backend.model.dto.LoginRequest;
import com.springluis.backend.model.dto.LoginResponse;
import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.model.dto.error.PersonalizedErrorResponse;
import com.springluis.backend.services.implement.CustomUserDetailsService;
import com.springluis.backend.services.implement.UserServiceImp;
import com.springluis.backend.services.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class LoginController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserServiceImp userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getIdentificador(), request.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getIdentificador());
            final String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(LoginResponse.builder().token(token).avatar(getAvatarFromUserDetails(userDetails)).build());
        
        } catch (Exception e) {
            PersonalizedErrorResponse error = new PersonalizedErrorResponse("Error iniciando sesión.", e.getMessage(), e.getClass().getName(), e.getStackTrace().toString());
            log.error("Error adding favorite game: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    private String getAvatarFromUserDetails(UserDetails userDetails) {

        try {
            Optional<UserDto> user = userService.findByAnyIdentifier(userDetails.getUsername());
            return user.get().getAvatar();
        } catch (Exception e) {
            log.error("Error obteniendo avatar del usuario: {}", e.getMessage(), e);
            return AppConstants.DEFAULT_AVATAR;
        }

    }
}
