package com.springluis.backend.config.fixtures;

import com.springluis.backend.config.constant.AppConstants;
import com.springluis.backend.model.dto.UserDto;
import com.springluis.backend.repository.UserRepository;
import com.springluis.backend.services.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;


@Component
@Slf4j
public class UserFixtures implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    public UserFixtures(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        try {
            if (userRepository.count() > 0) {
            log.info("La base de datos ya contiene datos. No se crearán fixtures.");
            return;
        }

        log.info("No se encontraron usuarios. Creando datos iniciales...");

        // --- Crear usuario Administrador ---
        UserDto adminDto = new UserDto();
        adminDto.setUsername("admin");
        adminDto.setEmail("admin@example.com");
        adminDto.setKey("admin123"); // La contraseña se encriptará en el servicio
        adminDto.setBio("Soy el administrador principal del sistema.");
        
        UserDto createdAdmin = userService.createNewUser(adminDto);

        // El servicio actual solo asigna ROLE_USER, así que actualizamos la entidad directamente
        userRepository.findById(createdAdmin.getId()).ifPresent(adminEntity -> {
            adminEntity.setRoles(new ArrayList<>(Arrays.asList(AppConstants.ROLE_ADMIN, AppConstants.ROLE_USER)));
            userRepository.save(adminEntity);
            log.info("Usuario 'admin' actualizado con roles de administrador.");
        });

        // --- Crear usuario Normal ---
        UserDto regularUser = new UserDto();
        regularUser.setUsername("luis");
        regularUser.setEmail("luis@example.com");
        regularUser.setKey("luis123");
        regularUser.setBio("Hola, soy un usuario de prueba.");
        userService.createNewUser(regularUser);

        log.info("Datos iniciales creados con éxito. Usuarios creados: admin, luis");
        } catch (Exception e) {
            log.error("Error al crear los datos iniciales: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear los datos iniciales", e);
        }

        
    }
}
