package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IItemRepository;
import com.giuseppemarket.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {
    private final IItemRepository itemRepository;

    @Override
    public Item venderItem(Integer idProduc) {
        Item itemMasViejoSinVender = itemRepository.findItemMasViejoSinVender(idProduc);
        itemMasViejoSinVender.setFechaVenta(LocalDate.now());
        itemRepository.save(itemMasViejoSinVender);
        return itemMasViejoSinVender;
    }

    @Override
    public String crear(ItemCrearRequestDTO itemCrearRequestDTO) {
        Item itemNew = Item.builder()
                .build();
        itemRepository.save(itemNew);
        return "SE CREO EL ITEM:" + itemNew.getId();
    }

    @Override
    public String obtenerItemsDisponiblesDeProducto(Integer idProducto) {
        return "";
    }

    @Override
    public String remove(Integer idItem) {
        return "";
    }



}
