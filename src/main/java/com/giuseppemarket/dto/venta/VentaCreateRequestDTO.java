
package com.giuseppemarket.dto.venta;

import com.giuseppemarket.utils.enums.CondicionVenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaCreateRequestDTO {
    CondicionVenta condicionVenta;
    List<Integer> idproductos;
    String observaciones;
    double descuento;

}
