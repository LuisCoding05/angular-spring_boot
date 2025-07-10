package com.springluis.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springluis.backend.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
    User findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);

}
