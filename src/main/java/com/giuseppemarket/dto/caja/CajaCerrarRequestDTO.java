package com.giuseppemarket.dto.caja;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CajaCerrarRequestDTO {

    String observacionesCaja;
    MontoRealRequestDTO montoRealDTO;
}
