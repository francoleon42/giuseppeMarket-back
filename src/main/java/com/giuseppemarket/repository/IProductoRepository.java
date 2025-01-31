package com.giuseppemarket.repository;

import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}
