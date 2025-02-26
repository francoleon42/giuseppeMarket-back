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
public class ProductosVendidosRequestDTO {

    Instant fechaDesde;
    Instant fechaHasta;
}
