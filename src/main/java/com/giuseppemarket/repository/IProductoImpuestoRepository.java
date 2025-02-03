package com.giuseppemarket.repository;


import com.giuseppemarket.model.ProductoImpuesto;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductoImpuestoRepository extends JpaRepository<ProductoImpuesto, Integer> {
    List<ProductoImpuesto> findByProductoId(Integer productoId);

    ProductoImpuesto findByProductoIdAndImpuestoId(Integer productoId, Integer impuestoId);


}
