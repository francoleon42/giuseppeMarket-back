package com.giuseppemarket.repository;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findBySucursal(Sucursal sucursal);

    @Query("SELECT p FROM Producto p WHERE p.sucursal = :sucursal1 OR p.sucursal = :sucursal2 OR p.sucursal = :sucursal3 OR p.sucursal = :sucursal4 OR p.sucursal = :sucursal5 OR p.sucursal = :sucursal6 OR p.sucursal = :sucursal7 OR p.sucursal = :sucursal8 OR p.sucursal = :sucursal9 OR p.sucursal = :sucursal10")
    List<Producto> findBySucursalesConOR(
            @Param("sucursal1") Sucursal sucursal1,
            @Param("sucursal2") Sucursal sucursal2,
            @Param("sucursal3") Sucursal sucursal3,
            @Param("sucursal4") Sucursal sucursal4,
            @Param("sucursal5") Sucursal sucursal5,
            @Param("sucursal6") Sucursal sucursal6,
            @Param("sucursal7") Sucursal sucursal7,
            @Param("sucursal8") Sucursal sucursal8,
            @Param("sucursal9") Sucursal sucursal9,
            @Param("sucursal10") Sucursal sucursal10
    );

    Optional<Producto> findByCodigoBarras(String codigoBarras);

    @Query("SELECT p FROM Producto p WHERE LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')) AND p.estado = 'HABILITADO'")
    List<Producto> findByCategoriaIgnoringCase(@Param("categoria") String categoria);


    @Query("SELECT p FROM Producto p WHERE p.stockActual < p.stockMinimo")
    List<Producto> findConDeficitStock();

}
