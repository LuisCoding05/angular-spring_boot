package com.springluis.backend.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String avatar;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bio;

    @Column(name = "pass", nullable = false)
    private String key;

    @ElementCollection
    @Column(nullable = false)
    private List<String> roles;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
