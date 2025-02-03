package com.giuseppemarket.service;

import com.giuseppemarket.dto.venta.*;
import com.giuseppemarket.utils.enums.CondicionVenta;

import java.util.List;

public interface IVentaService {
    VentaResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO, Integer idUsuario);
    List<CondicionVenta> obtenerCondicionesVenta();

    List<VentasPorFechasResponseDTO> historialVentasPorFechas(VentasPorFechasRequestDTO ventasPorFechasRequestDTO);


    List<ProductosVendidosResponseDTO> obtenerProductosVendidos(ProductosVendidosRequestDTO productosVendidosRequestDTO);

}
