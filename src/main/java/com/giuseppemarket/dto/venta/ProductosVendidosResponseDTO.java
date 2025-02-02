package com.giuseppemarket.dto.venta;

import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductosVendidosResponseDTO {
    ProductoResponseDTO productoResponseDTO;
    Integer cantidadDeVentas;

}
