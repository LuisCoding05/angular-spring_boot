package com.springluis.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springluis.backend.model.entity.FavoriteGame;

@Repository
public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, Long>{
    
    List<FavoriteGame> findByUserId(Long userId);

    @Query( nativeQuery = true, value = "SELECT * FROM favorite_game WHERE rawg_id = :rawgId LIMIT 1")
    Optional<FavoriteGame> findByRawgId(@Param("rawgId") Long rawgId);

} 
