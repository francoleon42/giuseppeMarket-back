package com.giuseppemarket.repository;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.utils.enums.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findBySucursal(Sucursal sucursal);
}
