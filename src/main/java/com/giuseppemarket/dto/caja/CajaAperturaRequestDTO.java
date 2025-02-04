package com.giuseppemarket.dto.caja;


import com.giuseppemarket.model.MontoReal;
import com.giuseppemarket.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CajaAperturaRequestDTO {

         double montoInicial;

}
