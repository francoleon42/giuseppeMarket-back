package com.giuseppemarket.dto.producto;


import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {
    String nombre;
    String marca;
    String descripcion;
    double costo;
    double porcentajeGanancia;
    double descuento;
    String codigoBarras;
    Integer stockMinimo;
    Integer stockMaximo;
    String categoria;
    String fabricante;
    String proveedor;
    //
    Estado estado;
    Sucursal sucursal;
    CondicionProducto condicionProducto;

}
