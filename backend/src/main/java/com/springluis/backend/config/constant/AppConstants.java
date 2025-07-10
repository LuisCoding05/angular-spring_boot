package com.springluis.backend.config.constant;

/**
 * Clase final para almacenar constantes de la aplicación.
 * El constructor privado previene que esta clase sea instanciada.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevenir instanciación
    }

    // --- ROLES DE USUARIO ---
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // --- VALORES POR DEFECTO ---
    public static final String DEFAULT_AVATAR = "https://res.cloudinary.com/dlgpvjulu/image/upload/v1744483544/default_bumnyb.webp";

}