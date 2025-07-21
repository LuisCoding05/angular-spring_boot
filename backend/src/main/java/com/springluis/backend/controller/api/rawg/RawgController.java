package com.springluis.backend.controller.api.rawg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springluis.backend.services.implement.GameServiceImp;
import com.springluis.backend.services.implement.JwtServiceImp;
import com.springluis.backend.model.dto.FavoriteGameDto;
import com.springluis.backend.model.dto.api.rawg.RawgGame;
import com.springluis.backend.model.dto.api.rawg.RawgGameDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class RawgController {

    @Autowired
    private final JwtServiceImp jwtService;

    @Autowired
    private final GameServiceImp gameService;

    @GetMapping
    public ResponseEntity<?> getGames(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "4", name = "page_size") int pageSize,
        @RequestParam(defaultValue = "") String search
    ) {
        try {
            Page<RawgGame> games = gameService.findAllGames(page, pageSize, search);
            return ResponseEntity.ok(games);
        } catch (Exception e) {
            log.error("Error retrieving games: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new java.util.HashMap<String, Object>() {{
                    put("error", "Error retrieving games.");
                    put("exception", e.getClass().getName());
                    put("message", e.getMessage());
                    put("stackTrace", e.getStackTrace());
                }}
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable Long id) {
        try {
            RawgGameDetails game = gameService.findGameById(id);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            log.error("Error retrieving game with id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new java.util.HashMap<String, Object>() {{
                    put("error", "Error retrieving game details.");
                    put("exception", e.getClass().getName());
                    put("message", e.getMessage());
                    put("stackTrace", e.getStackTrace());
                }}
            );
        }
    }

    @PostMapping("/add-to-favorites")
    public ResponseEntity<?> addFavoriteGame(@RequestBody FavoriteGameDto dto, @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El token de autorización es inválido o no se proporcionó.");
        }

        final String token = authHeader.substring(7);
        final String username = jwtService.extractUsername(token);
        gameService.addFavoriteGame(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
