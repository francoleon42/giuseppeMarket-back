package com.giuseppemarket.dto.caja;

import com.giuseppemarket.dto.login.GetUserDTO;
import com.giuseppemarket.model.MontoReal;
import com.giuseppemarket.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CajaResponseDTO {
    Integer id;
    Instant apertura;
    Instant cierre;
    double montoInicial;
    double montoFinal;
    String observaciones;
    GetUserDTO usuario;
}
