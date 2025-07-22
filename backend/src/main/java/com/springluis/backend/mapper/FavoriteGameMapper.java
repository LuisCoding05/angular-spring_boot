package com.springluis.backend.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.springluis.backend.model.dto.FavoriteGameDto;
import com.springluis.backend.model.entity.FavoriteGame;
import com.springluis.backend.model.entity.User;

@Component("favoriteGameMapper")
public class FavoriteGameMapper implements Mapper<FavoriteGame, FavoriteGameDto> {

    private final UserMapper userMapper;

    public FavoriteGameMapper(@Qualifier("userMapper") UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public FavoriteGameDto toTarget(FavoriteGame source) {
        if (source == null) {
            return null;
        }
        return FavoriteGameDto.builder()
                .id( source.getId() == 0 ? null : source.getId())
                .rawgId(source.getRawgId())
                .title(source.getTitle())
                .releaseDate(source.getReleaseDate())
                .backgroundImage(source.getBackgroundImage())
                .rawgRating(source.getRawgRating())
                .personalReview(source.getPersonalReview())
                .personalRating(source.getPersonalRating())
                .platforms(source.getPlatforms())
                .userDto(source.getUser() != null ? userMapper.toTarget(source.getUser()) : null)
                .build();
    }

    @Override
    public FavoriteGame toSource(FavoriteGameDto target) {
        if (target == null) {
            return null;
        }

        User user = target.getUserDto() != null ? userMapper.toSource(target.getUserDto()) : null;

        return FavoriteGame.builder()
                .id(target.getId() == 0 ? null : target.getId())
                .rawgId(target.getRawgId())
                .title(target.getTitle())
                .releaseDate(target.getReleaseDate())
                .backgroundImage(target.getBackgroundImage())
                .rawgRating(target.getRawgRating())
                .personalReview(target.getPersonalReview() != null ? target.getPersonalReview() : "")
                .personalRating(target.getPersonalRating())
                .platforms(target.getPlatforms())
                .user(user)
                .build();
    }
}