package com.springluis.backend.model.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FavoriteGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long rawgId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false, length = 512)
    private String backgroundImage;

    @Column(nullable = false)
    private Double rawgRating;
    
    @Column(columnDefinition = "TEXT", nullable = true)
    private String personalReview;

    @Column(name = "personal_rating")
    private Double personalRating;

    @ElementCollection
    private List<String> platforms;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
