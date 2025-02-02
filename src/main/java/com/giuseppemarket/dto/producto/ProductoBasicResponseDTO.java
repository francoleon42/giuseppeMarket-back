package com.giuseppemarket.dto.producto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoBasicResponseDTO {

    Integer id;
    String codigoBarras;
    String categoria;
    String nombre;
    String marca;
    String proveedor;

}
