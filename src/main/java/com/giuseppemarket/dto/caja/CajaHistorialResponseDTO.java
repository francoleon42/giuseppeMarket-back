package com.giuseppemarket.dto.caja;

import com.giuseppemarket.model.MontoReal;
import com.giuseppemarket.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CajaHistorialResponseDTO {
        CajaResponseDTO cajaResponseDTO;
        MontoRealResponseDTO montoRealResponseDTO;
}
