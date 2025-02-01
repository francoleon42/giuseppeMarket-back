package com.giuseppemarket.dto.producto;

import com.giuseppemarket.utils.enums.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoViewByVentaResponseDTO {
     Integer id;
     String categoria;
     String codigoBarras;
     String nombre;
     String marca;
     String descripcion;
     String sucursal;
     String condicionProducto;
     Integer stockActual;
     Integer stockMinimo;
     Integer stockMaximo;
     Float costo;
     double porcentajeGanancia;
     double ganancia;
     double precio;
     double descuento;
     Estado estado;
     String fabricante;
     String proveedor;
}
