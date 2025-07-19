package com.springluis.backend.model.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteGameDto {
    private Long id;
    private Long rawgId;
    private String title;
    private LocalDate releaseDate;
    private String backgroundImage;
    private Double rawgRating;
    private String personalReview;
    private Double personalRating;
    private List<String> platforms;
    private UserDto userDto;
}
