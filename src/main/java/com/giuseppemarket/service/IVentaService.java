package com.giuseppemarket.service;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.dto.venta.VentaResponseDTO;
import com.giuseppemarket.dto.venta.VentasPorFechasRequestDTO;
import com.giuseppemarket.dto.venta.VentasPorFechasResponseDTO;
import com.giuseppemarket.utils.enums.CondicionVenta;

import java.util.List;

public interface IVentaService {
    VentaResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO, Integer idUsuario);
    List<CondicionVenta> obtenerCondicionesVenta();

    List<VentasPorFechasResponseDTO> historialVentasPorFechas(VentasPorFechasRequestDTO ventasPorFechasRequestDTO);


}
