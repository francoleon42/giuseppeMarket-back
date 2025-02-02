package com.giuseppemarket.dto.impuesto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImpuestoResponseDTO {
    Integer id;
    double valor;
    String nombre;
}
