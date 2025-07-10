package com.springluis.backend.services.implement;

import com.springluis.backend.config.constant.AppConstants;
import com.springluis.backend.mapper.UserMapper;
import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.model.entity.User;
import com.springluis.backend.repository.UserRepository;
import com.springluis.backend.security.Encriptar;
import com.springluis.backend.services.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImp implements UserService {
    
    @Qualifier("userMapper")
    private final UserMapper userMapper;

    @Autowired
    private final UserRepository userRepository;
    
    public UserServiceImp(@Qualifier("userMapper") UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto createNewUser(UserDto userDto) {
        if (log.isDebugEnabled()) {
            log.debug("Creando nuevo usuario: {}", userDto.getUsername());
        }
        try {
            // Mapeamos el DTO a la entidad
            User newUser = userMapper.toSource(userDto);

            // Lógica de negocio que antes estaba en el constructor
            newUser.setKey(Encriptar.encriptarPassword(userDto.getKey()));
            newUser.setRoles(new ArrayList<>(Collections.singletonList(AppConstants.ROLE_USER)));
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setAvatar(AppConstants.DEFAULT_AVATAR);

            // Guardamos la nueva entidad
            User savedUser = userRepository.save(newUser);
            
            log.info("Usuario creado con éxito: {}", savedUser.getUsername());

            // Mapeamos la entidad guardada de vuelta a DTO para la respuesta
            return userMapper.toTarget(savedUser);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error al crear un nuevo usuario: {}", e.getMessage(), e);
            }
            // En un caso real, sería mejor lanzar una excepción personalizada
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        try {
            log.debug("Buscando usuario por id: {}", id);
            return userRepository.findById(id).map(userMapper::toTarget);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error al buscar usuario por id {}: {}", id, e.getMessage(), e);
            }
            return Optional.empty();
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {

        try {
            log.debug("Buscando todos los usuarios");
            return userRepository.findAll()
                .stream()
                .map(userMapper::toTarget)
                .collect(Collectors.toList());
        } catch (Exception e) {

            if (log.isErrorEnabled()) {
                log.error("Error al buscar todos los usuarios: {}", e.getMessage(), e);
            }
            return Collections.emptyList();
        }
        
    }
}
