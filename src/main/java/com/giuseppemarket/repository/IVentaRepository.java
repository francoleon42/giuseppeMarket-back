package com.giuseppemarket.repository;

import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVentaRepository extends JpaRepository<Venta, Integer> {
}
