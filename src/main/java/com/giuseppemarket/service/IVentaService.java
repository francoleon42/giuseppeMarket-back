package com.giuseppemarket.service;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;

public interface IVentaService {
    String realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO);
}
