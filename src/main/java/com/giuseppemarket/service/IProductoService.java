package com.giuseppemarket.service;

import com.giuseppemarket.dto.producto.ProductoRequestDTO;
import com.giuseppemarket.dto.producto.ProductoViewByVentaResponseDTO;
import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;

import java.util.List;

public interface IProductoService {
    void disminuirStock(Integer idProducto);
    double subtotalDeProductos(List<Integer> idProductos);
    List<ProductoViewByVentaResponseDTO> obtenerProductosBySucursal(Sucursal sucursal);
    List<ProductoViewByVentaResponseDTO> obtenerProductosByCodigoBarra(String codigoBarra);
    List<ProductoViewByVentaResponseDTO> obtenerProductosByCategoria(String categoria);

    List<Estado> obtenerEstados();
    List<Sucursal> obtenerSucursales();
    List<CondicionProducto> obtenerCondicionProducto();
    String crear(ProductoRequestDTO productoRequestDTO);

    String update(ProductoRequestDTO productoRequestDTO,Integer idProducto);

}
