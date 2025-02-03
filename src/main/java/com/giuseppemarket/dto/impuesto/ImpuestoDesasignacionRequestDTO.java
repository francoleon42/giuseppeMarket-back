package com.giuseppemarket.dto.impuesto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImpuestoDesasignacionRequestDTO {
    Integer idProducto;
    Integer idImpuesto;
}
