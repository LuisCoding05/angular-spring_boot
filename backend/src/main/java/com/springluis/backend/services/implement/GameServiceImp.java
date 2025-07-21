package com.springluis.backend.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.springluis.backend.mapper.FavoriteGameMapper;
import com.springluis.backend.mapper.UserMapper;
import com.springluis.backend.repository.FavoriteGameRepository;
import com.springluis.backend.services.service.GameService;

import lombok.extern.slf4j.Slf4j;

import com.springluis.backend.model.dto.FavoriteGameDto;
import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.model.dto.api.rawg.RawgGame;
import com.springluis.backend.model.dto.api.rawg.RawgGameDetails;
import com.springluis.backend.model.dto.api.rawg.RawgResponse;
import com.springluis.backend.model.entity.FavoriteGame;

@Slf4j
@Service
public class GameServiceImp implements GameService {

    private final RestTemplate restTemplate;
    private final FavoriteGameRepository favoriteGameRepository;

    @Autowired
    private final UserServiceImp userService;
    @Qualifier("favoriteGameMapper")
    private final FavoriteGameMapper gameMapper;

    @Qualifier("userMapper")
    private final UserMapper userMapper;

    @Value("${rawg.api.key}")
    private String apiKey;

    public GameServiceImp(RestTemplateBuilder restTemplateBuilder,
                           FavoriteGameRepository favoriteGameRepository,
                           UserServiceImp userService,
                           FavoriteGameMapper gameMapper,UserMapper userMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.favoriteGameRepository = favoriteGameRepository;
        this.userService = userService;
        this.gameMapper = gameMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Page<RawgGame> findAllGames(int page, int pageSize, String search) {
        String url = UriComponentsBuilder.fromUriString("https://api.rawg.io/api/games")
                .queryParam("key", apiKey)
                .queryParam("page", page)
                .queryParam("page_size", pageSize)
                .queryParam("search", search)
                .toUriString();

        
        if(log.isDebugEnabled()) {
            log.debug("Fetching games from URL: {}", url);
        }
        ResponseEntity<RawgResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RawgResponse>() {}
        );

        RawgResponse rawgResponse = response.getBody();
        List<RawgGame> games = rawgResponse != null ? rawgResponse.getResults() : List.of();
        int total = rawgResponse != null ? rawgResponse.getCount() : 0;

        return new PageImpl<>(games, PageRequest.of(page - 1, pageSize), total);
    }

    @Override
    public void addFavoriteGame(FavoriteGameDto dto, String userId) {
        UserDto user = userService.findByAnyIdentifier(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        FavoriteGame game = gameMapper.toSource(dto);
        game.setUser(userMapper.toSource(user));
        favoriteGameRepository.save(game);
    }

    @Override
    public RawgGameDetails findGameById(Long id) {
        String url = UriComponentsBuilder.fromUriString("https://api.rawg.io/api/games/{id}")
                .queryParam("key", apiKey)
                .buildAndExpand(id)
                .toUriString();

        if (log.isDebugEnabled()) {
            log.debug("Fetching game details from URL: {}", url);
        }

        ResponseEntity<RawgGameDetails> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<RawgGameDetails>() {});

        return response.getBody();
    }
}
