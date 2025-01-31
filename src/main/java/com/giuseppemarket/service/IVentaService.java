package com.giuseppemarket.service;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateResponseDTO;

public interface IVentaService {
    VentaCreateResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO);
}
