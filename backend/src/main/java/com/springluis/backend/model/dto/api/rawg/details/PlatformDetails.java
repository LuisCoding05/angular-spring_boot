package com.springluis.backend.model.dto.api.rawg.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springluis.backend.model.dto.api.rawg.platform.Platform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformDetails {
    private Platform platform;

    @JsonProperty("released_at")
    private String releasedAt;

    private Requirements requirements;
}