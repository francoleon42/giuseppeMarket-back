package com.giuseppemarket.dto.impuesto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoAsignacionResponseDTO {

    Integer id;
    String codigoBarras;
    String categoria;
    String nombre;
    String marca;
    String proveedor;

}
