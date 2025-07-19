package com.springluis.backend.model.dto.api.rawg;

import java.util.List;

import lombok.Data;

@Data
public class RawgResponse {
    private int count;
    private String next;
    private String previous;
    private List<RawgGame> results;
}

