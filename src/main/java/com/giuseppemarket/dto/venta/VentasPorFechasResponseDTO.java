package com.giuseppemarket.dto.venta;

import com.giuseppemarket.dto.item.ItemViewResponseDTO;
import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
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
public class VentasPorFechasResponseDTO {
    VentaResponseDTO ventaResponseDTO;
    List<ProductoResponseDTO> productosResponseDTO;
}
