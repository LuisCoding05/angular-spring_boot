package com.springluis.backend.services.service;

import com.springluis.backend.model.dto.UserDto;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createNewUser(UserDto userDto);

    Optional<UserDto> findById(Long id);

    List<UserDto> findAll();

    // Aquí podrías añadir otros métodos como updateUser, deleteUser, etc.
}

