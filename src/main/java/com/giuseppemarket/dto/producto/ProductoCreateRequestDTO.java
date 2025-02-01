package com.giuseppemarket.dto.producto;


import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCreateRequestDTO {
    String nombre;
    String marca;
    String descripcion;
    Float costo;
    double porcentajeGanancia;
    double descuento;
    String codigoBarras;
    Integer stockActual;
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
