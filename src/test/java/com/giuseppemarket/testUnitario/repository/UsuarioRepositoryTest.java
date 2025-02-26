package com.giuseppemarket.testUnitario.repository;

import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.repository.IUsuarioRepository;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Rol;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Test
    @Transactional
    @Rollback
    void guardarUsuario() {
        Usuario usuario = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.VENDEDOR)
                .estadoUsuario(Estado.HABILITADO)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        assertNotNull(usuarioGuardado);
        assertNotNull(usuarioGuardado.getId());
        assertEquals("operador", usuarioGuardado.getUsuario());
        assertEquals(Rol.VENDEDOR, usuarioGuardado.getRol());
    }

    @Test
    void encontrarUsuarioPorNombre() {
        Usuario usuario = Usuario.builder()
                .usuario("vendedor")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.VENDEDOR)
                .estadoUsuario(Estado.HABILITADO)
                .build();

        usuarioRepository.save(usuario);

        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsuario("vendedor");

        assertTrue(usuarioEncontrado.isPresent());
        assertEquals("vendedor", usuarioEncontrado.get().getUsuario());
    }

}
