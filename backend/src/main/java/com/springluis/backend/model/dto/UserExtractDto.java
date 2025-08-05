package com.springluis.backend.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserExtractDto {

    private final UserDto userDto;
    
    private final List<FavoriteGameDto> favoriteGames;

}
