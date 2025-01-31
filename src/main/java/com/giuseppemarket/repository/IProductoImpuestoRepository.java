package com.giuseppemarket.repository;


import com.giuseppemarket.model.ProductoImpuesto;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoImpuestoRepository extends JpaRepository<ProductoImpuesto, Integer> {
}
