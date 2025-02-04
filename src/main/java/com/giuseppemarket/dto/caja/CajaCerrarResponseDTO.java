package com.giuseppemarket.dto.caja;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CajaCerrarResponseDTO {
    double montoFinal;
    double montoReal;
    double resultado;
    String estado;
}
