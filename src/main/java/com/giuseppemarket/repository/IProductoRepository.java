package com.giuseppemarket.repository;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.utils.enums.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findBySucursal(Sucursal sucursal);

    List<Producto> findByCodigoBarras(String codBarra);

    @Query("SELECT p FROM Producto p WHERE p.categoria LIKE %:categoria%")
    List<Producto> findByCategoriaContaining(@Param("categoria") String categoria);

    @Query("SELECT p FROM Producto p WHERE p.stockActual < p.stockMinimo")
    List<Producto> findConDeficitStock();

}
