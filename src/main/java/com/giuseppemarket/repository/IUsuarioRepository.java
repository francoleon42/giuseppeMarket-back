package com.giuseppemarket.repository;

import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsuario(String usuario);
    Boolean existsByUsuario(String username);
}