package com.giuseppemarket.repository;

import com.giuseppemarket.model.Caja;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICajaRepository extends JpaRepository<Caja, Integer> {
    Optional<Caja> findByUsuarioIdAndCierreIsNull(Integer usuarioId);

    @Query("SELECT c FROM Caja c WHERE c.apertura BETWEEN :fechaInicio AND :fechaFin")
    List<Caja> findCajasByFechaAperturaBetween(@Param("fechaInicio") Instant fechaInicio, @Param("fechaFin") Instant fechaFin);
}
