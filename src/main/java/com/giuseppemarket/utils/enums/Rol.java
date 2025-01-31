package com.giuseppemarket.utils.enums;

public enum Rol {
    ADMINISTRADOR,
    VENDEDOR;

    public static Rol getRol(String rol) {
        return Rol.valueOf(rol.toUpperCase());
    }
}