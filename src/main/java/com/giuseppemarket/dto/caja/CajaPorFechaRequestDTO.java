package com.giuseppemarket.dto.caja;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CajaPorFechaRequestDTO {
    Instant desde;
    Instant hasta;
}
