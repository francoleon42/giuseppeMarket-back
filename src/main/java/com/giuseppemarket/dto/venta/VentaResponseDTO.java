package com.giuseppemarket.dto.venta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaResponseDTO {
    Integer comprobante;
    Instant fechaHora;
    String observaciones;
    double subtotal;
    double descuento;
    double total;
    String condicionVenta;


}
