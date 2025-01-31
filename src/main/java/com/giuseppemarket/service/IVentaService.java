package com.giuseppemarket.service;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateResponseDTO;
import com.giuseppemarket.utils.enums.CondicionVenta;

import java.util.List;

public interface IVentaService {
    VentaCreateResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO,Integer idUsuario);
    List<CondicionVenta> obtenerCondicionesVenta();
}
