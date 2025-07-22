package com.springluis.backend.controller.dashboard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springluis.backend.model.dto.FavoriteGameDto;
import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.services.implement.UserServiceImp;
import com.springluis.backend.services.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import com.springluis.backend.services.implement.GameServiceImp;


@RestController
@RequestMapping("/api/dashboard/")
@RequiredArgsConstructor
@Slf4j
public class DashboardIndexController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserServiceImp userService;

    @Autowired
    private final GameServiceImp gameService;

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
                Map<String, Object> error = new java.util.HashMap<>();
                error.put("error", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            user.get().setKey(null);

            // Obtener juegos favoritos del usuario
            Long userId = user.get().getId();
            List<FavoriteGameDto> favoriteGames = gameService.findFavoritesByUserId(userId);

            Map<String, Object> response = new java.util.HashMap<>();
            response.put("user", user.get());
            response.put("favoriteGames", favoriteGames);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new java.util.HashMap<>();
            error.put("error", "Token inválido");
            error.put("message", e.getMessage());
            log.error("Error in DashboardIndexController: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
