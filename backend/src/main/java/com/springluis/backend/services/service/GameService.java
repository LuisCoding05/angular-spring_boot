package com.springluis.backend.services.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springluis.backend.model.dto.FavoriteGameDto;
import com.springluis.backend.model.dto.api.rawg.RawgGame;
import com.springluis.backend.model.dto.api.rawg.RawgGameDetails;

public interface GameService {
    Page<RawgGame> findAllGames(int page, int pageSize, String search);
    void addFavoriteGame(FavoriteGameDto favoriteGameDto, String userId);
    RawgGameDetails findGameById(Long id);
    List<FavoriteGameDto> findFavoritesByUserId(Long userId);

}

