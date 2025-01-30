package com.giuseppemarket.config;


import com.giuseppemarket.model.*;
import com.giuseppemarket.repository.*;
import com.giuseppemarket.utils.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear usuarios con builder
        Usuario admin = Usuario.builder()
                .usuario("admin")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .rol(Rol.ADMINISTRADOR)
                .build();

        Usuario vendedor = Usuario.builder()
                .usuario("vendedor")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .rol(Rol.VENDEDOR)
                .build();


        Usuario choferu = Usuario.builder()
                .usuario("gerente")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .rol(Rol.CHOFER)
                .build();

        userRepository.saveAll(List.of(admin, vendedor, choferu ));



    }

}
