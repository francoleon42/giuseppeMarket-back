package com.gifa_api.repository;

import com.gifa_api.model.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IChoferRepository extends JpaRepository<Chofer, Integer> {
    Optional<Chofer> findByUsuario_Id(Integer usuarioId);

}
