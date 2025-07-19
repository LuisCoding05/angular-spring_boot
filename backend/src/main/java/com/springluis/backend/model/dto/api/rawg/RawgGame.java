package com.springluis.backend.model.dto.api.rawg;

import java.util.List;

import com.springluis.backend.model.dto.api.rawg.genre.Genre;
import com.springluis.backend.model.dto.api.rawg.platform.PlatformWrapper;
import com.springluis.backend.model.dto.api.rawg.store.StoreWrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawgGame {
    private Long id;
    private String slug;
    private String name;
    private String released;
    private Double rating;
    private Integer metacritic;
    private String background_image;
    private List<PlatformWrapper> platforms;
    private List<Genre> genres;
    private List<StoreWrapper> stores;
}

