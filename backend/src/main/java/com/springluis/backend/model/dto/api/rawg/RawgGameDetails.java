package com.springluis.backend.model.dto.api.rawg;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springluis.backend.model.dto.api.rawg.details.Rating;
import com.springluis.backend.model.dto.api.rawg.details.EsrbRating;
import com.springluis.backend.model.dto.api.rawg.details.MetacriticPlatform;
import com.springluis.backend.model.dto.api.rawg.details.PlatformDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawgGameDetails {
    private Long id;
    private String slug;
    private String name;

    @JsonProperty("name_original")
    private String nameOriginal;
    private String description;
    private Integer metacritic;

    @JsonProperty("metacritic_platforms")
    private List<MetacriticPlatform> metacriticPlatforms;
    private String released;
    private boolean tba;
    private String updated;

    @JsonProperty("background_image")
    private String backgroundImage;

    @JsonProperty("background_image_additional")
    private String backgroundImageAdditional;
    private String website;
    private Double rating;

    @JsonProperty("rating_top")
    private Integer ratingTop;
    private List<Rating> ratings;
    private Map<String, Object> reactions;
    private Integer playtime;

    @JsonProperty("suggestions_count")
    private Integer suggestionsCount;

    @JsonProperty("metacritic_url")
    private String metacriticUrl;
    
    @JsonProperty("esrb_rating")
    private EsrbRating esrbRating;
    private List<PlatformDetails> platforms;
}