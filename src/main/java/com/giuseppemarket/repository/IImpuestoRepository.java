package com.giuseppemarket.repository;

import com.giuseppemarket.model.Impuesto;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImpuestoRepository extends JpaRepository<Impuesto, Integer> {
}
