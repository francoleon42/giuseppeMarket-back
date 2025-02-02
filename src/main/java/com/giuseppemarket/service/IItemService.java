package com.giuseppemarket.service;

import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Venta;

public interface IItemService {
    Item venderItem(Integer idProducto);

    String crear(ItemCrearRequestDTO itemCrearRequestDTO);
    String obtenerItemsDisponiblesDeProducto(Integer idProducto);
    String remove(Integer idItem);
}
