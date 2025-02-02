package com.giuseppemarket.repository;

import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface IVentaRepository extends JpaRepository<Venta, Integer> {

    @Query("SELECT v FROM Venta v WHERE v.fechaHora BETWEEN :fechaDesde AND :fechaHasta")
    List<Venta> findByFechaHoraBetween(@Param("fechaDesde") Instant fechaDesde, @Param("fechaHasta") Instant fechaHasta);
}
