package com.giuseppemarket.service;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Venta;

public interface IItemService {
    Item venderItem(Integer idProducto);

    String crearItem();
}
