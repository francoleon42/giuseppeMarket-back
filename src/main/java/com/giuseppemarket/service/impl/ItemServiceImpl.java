package com.giuseppemarket.service.impl;

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
    public String crearItem() {
        return "";
    }


}
