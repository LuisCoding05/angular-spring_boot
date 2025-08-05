package com.springluis.backend.model.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PersonalizedErrorResponse {

    public final String error;
    public final String message;
    public String exception;
    public String stackTrace;
}
