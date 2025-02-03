package com.giuseppemarket.repository;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IItemRepository extends JpaRepository<Item, Integer> {

    @Query("""
    SELECT i FROM Item i
    WHERE i.producto.id = :idProducto 
    AND i.fechaVenta IS NULL
    AND (i.vencimiento IS NULL OR i.vencimiento >= :fechaActual)
    ORDER BY i.vencimiento ASC NULLS FIRST
    LIMIT 1
""")
    Item findItemMasCercanoSinVencer(@Param("idProducto") Integer idProducto, @Param("fechaActual") LocalDate fechaActual);


    List<Item> findByProductoIdAndFechaVentaIsNull(Integer idProducto);

    List<Item> findByVencimientoLessThanEqual(LocalDate fecha);


    List<Item> findByNumeroLote(Integer numeroLote);


    @Query("SELECT DISTINCT i.numeroLote FROM Item i")
    List<Integer> findDistinctNumeroLote();
}
