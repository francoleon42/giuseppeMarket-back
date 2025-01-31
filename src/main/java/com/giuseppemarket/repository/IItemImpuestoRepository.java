package com.giuseppemarket.repository;

import com.giuseppemarket.model.ItemImpuesto;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemImpuestoRepository extends JpaRepository<ItemImpuesto, Integer> {
}
