package com.giuseppemarket.service;

import com.giuseppemarket.dto.ProductoViewBySucursalResponseDTO;
import com.giuseppemarket.utils.enums.Sucursal;

import java.util.List;

public interface IProductoService {
    void disminuirStock(Integer idProducto);
    double subtotalDeProductos(List<Integer> idProductos);
    List<ProductoViewBySucursalResponseDTO> obtenerProductosBySucursal(Sucursal sucursal);
}
