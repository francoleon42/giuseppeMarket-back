package com.giuseppemarket.service;

import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import com.giuseppemarket.dto.producto.ProductoRequestDTO;
import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;

import java.util.List;

public interface IProductoService {
    Item disminuirStock(Integer idProducto);
    double subtotalDeProductos(List<Integer> idProductos);
    List<ProductoResponseDTO> obtenerProductosBySucursal(Sucursal sucursal);

    List<ProductoResponseDTO> obtenerProductosByCodigoBarra(String codigoBarra);
    List<ProductoResponseDTO> obtenerProductosByCategoria(String categoria);
    List<ProductoResponseDTO> obtenerAllProductos();
    List<ProductoResponseDTO> obtenerProductosMinorista();
    List<ProductoResponseDTO> obtenerProductosMayorista();

    List<Estado> obtenerEstados();
    List<Sucursal> obtenerSucursales();
    List<CondicionProducto> obtenerCondicionProducto();
    ProductoBasicResponseDTO crear(ProductoRequestDTO productoRequestDTO);
    void addImpuesto(Integer idProducto,double impuesto);
    void removeImpuesto(Integer idProducto,double impuesto);

    String update(ProductoRequestDTO productoRequestDTO,Integer idProducto);

    List<ProductoResponseDTO> obtenerProductoEnDeficitStock();

}
