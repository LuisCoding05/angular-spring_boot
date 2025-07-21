package com.springluis.backend.model.dto.api.rawg.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requirements {
    private String minimum;
    private String recommended;
}