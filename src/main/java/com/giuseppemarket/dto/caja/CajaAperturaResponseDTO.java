package com.giuseppemarket.dto.caja;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class CajaAperturaResponseDTO {
     Integer id;

    String apertura;

    double montoInicial;
    GetUserDTO usuario;
}
