package com.springluis.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springluis.backend.model.entity.FavoriteGame;

@Repository
public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, Long>{
    
    List<FavoriteGame> findByUserId(Long userId);
    
} 
