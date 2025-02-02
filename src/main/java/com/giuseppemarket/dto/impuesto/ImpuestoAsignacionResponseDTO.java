package com.giuseppemarket.dto.impuesto;


import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImpuestoAsignacionResponseDTO {

    ImpuestoResponseDTO impuesto;
    ProductoBasicResponseDTO producto;
}
