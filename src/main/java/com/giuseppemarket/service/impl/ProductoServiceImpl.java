package com.giuseppemarket.service.impl;

import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
    private final IProductoRepository productoRepository;
    private final IItemService itemService;

    @Override
    public void disminuirStock(Integer idProducto) {
        Producto producto = obtenerProductoById(idProducto);

        if (producto.getStockMinimo() > producto.getStockActual() - 1) {
            producto.setStockMinimo(producto.getStockMinimo() - 1);
            itemService.venderItem(producto.getId());
        }
        productoRepository.save(producto);

    }

    @Override
    public double subtotalDeProductos(List<Integer> idProductos) {
        double subtotal = 0.0;
        for (Integer idProducto : idProductos) {
            subtotal += obtenerProductoById(idProducto).getPrecio();
        }
        return subtotal;
    }

    private Producto obtenerProductoById(Integer id) {
        return productoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " + id));
    }
}
