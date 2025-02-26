package com.giuseppemarket.service;

import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.dto.item.ItemViewResponseDTO;
import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Venta;

import java.util.List;

public interface IItemService {
    Item venderItem(Integer idProducto);

    String crear(ItemCrearRequestDTO itemCrearRequestDTO);
    List<ItemViewResponseDTO> obtenerItemsDisponiblesDeProducto(Integer idProducto);
    String remove(Integer idItem);

    List<ItemViewResponseDTO> vencidosHoy();
    List<ItemViewResponseDTO> buscarPorLote(Integer numeroLote);
    List<Integer> obtenerAllNumerosLotes();

}
