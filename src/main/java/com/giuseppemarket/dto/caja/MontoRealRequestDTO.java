package com.giuseppemarket.dto.caja;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MontoRealRequestDTO {
    double montoTarjetas;
    double montoTransferencias;
    double montoEfectivo;
    double montoOtros;
    String observaciones;
}
