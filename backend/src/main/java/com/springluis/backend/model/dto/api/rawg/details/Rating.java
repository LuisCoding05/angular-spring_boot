package com.springluis.backend.model.dto.api.rawg.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Integer id;
    private String title;
    private Integer count;
    private Double percent;
}