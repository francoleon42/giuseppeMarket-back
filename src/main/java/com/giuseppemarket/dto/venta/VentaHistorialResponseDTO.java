package com.giuseppemarket.dto.venta;

import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaHistorialResponseDTO {
    VentaResponseDTO ventaResponseDTO;
    List<ProductoResponseDTO> productosResponseDTO;
}
