package com.giuseppemarket.service;

import com.giuseppemarket.dto.ProductoViewByVentaResponseDTO;
import com.giuseppemarket.utils.enums.Sucursal;

import java.util.List;

public interface IProductoService {
    void disminuirStock(Integer idProducto);
    double subtotalDeProductos(List<Integer> idProductos);
    List<ProductoViewByVentaResponseDTO> obtenerProductosBySucursal(Sucursal sucursal);
    List<ProductoViewByVentaResponseDTO> obtenerProductosByCodigoBarra(String codigoBarra);
    List<ProductoViewByVentaResponseDTO> obtenerProductosByCategoria(String categoria);

}
