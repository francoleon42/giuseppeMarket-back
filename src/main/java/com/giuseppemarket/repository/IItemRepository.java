package com.giuseppemarket.repository;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.producto.id = :idProducto AND i.fechaVenta IS NULL ORDER BY i.elaboracion ASC LIMIT 1")
    Item findItemMasViejoSinVender(@Param("idProducto") Integer idProducto);

    List<Item> findByProductoIdAndFechaVentaIsNull(Integer idProducto);

    List<Item> findByVencimientoLessThanEqual(LocalDate fecha);

}
