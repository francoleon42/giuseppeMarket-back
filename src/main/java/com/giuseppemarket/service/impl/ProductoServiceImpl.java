package com.giuseppemarket.service.impl;

import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.repository.IItemRepository;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
    private final IProductoRepository productoRepository;
    private final IItemService itemService;

    @Override
    public void disminuirStock(Integer idProducto) {
        Producto producto = productoRepository
                .findById(idProducto)
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " + idProducto));

        if(producto.getStockMinimo() > producto.getStockActual() - 1 ){
            producto.setStockMinimo(producto.getStockMinimo() - 1);
            itemService.venderItem(producto.getId());
        }

    }
}
