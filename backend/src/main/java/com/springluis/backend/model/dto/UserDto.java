package com.springluis.backend.model.dto;

import java.time.LocalDateTime;
import java.util.List; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String avatar;

    private String username;

    private String email;

    private String bio;

    private String key;

    private List<String> roles;

    private LocalDateTime createdAt;

}
