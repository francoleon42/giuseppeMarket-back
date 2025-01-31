package com.giuseppemarket.dto;

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
public class ProductoViewBySucursalResponseDTO {
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
