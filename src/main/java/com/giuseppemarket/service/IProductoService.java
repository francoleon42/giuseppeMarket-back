package com.giuseppemarket.service;

import java.util.List;

public interface IProductoService {
    void disminuirStock(Integer idProducto);
    double subtotalDeProductos(List<Integer> idProductos);
}
