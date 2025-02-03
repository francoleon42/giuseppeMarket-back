package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.dto.item.ItemViewResponseDTO;
import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IItemRepository;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IItemService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {
    private final IItemRepository itemRepository;
    private final IProductoRepository productoRepository; ;

    @Override
    public Item venderItem(Integer idProduc) {
        //TODO : REVISAR SI SE QUIERE VENDER PRODUCTOS VENCIDOS
        Item itemMasViejoSinVender = itemRepository.findItemMasViejoSinVender(idProduc);
        itemMasViejoSinVender.setFechaVenta(LocalDate.now());
        itemRepository.save(itemMasViejoSinVender);
        return itemMasViejoSinVender;
    }

    @Override
    public String crear(ItemCrearRequestDTO itemCrearRequestDTO) {
        Producto producto = productoRepository
                .findById(itemCrearRequestDTO.getIdProducto())
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " + itemCrearRequestDTO.getIdProducto()));


        Item itemNew = Item.builder()
                .vencimiento(itemCrearRequestDTO.getVencimiento())
                .elaboracion(itemCrearRequestDTO.getElaboracion())
                .numeroLote(itemCrearRequestDTO.getNumeroLote())
                .producto(producto)
                .build();

        itemRepository.save(itemNew);
        producto.getItems().add(itemNew);
        productoRepository.save(producto);

        return "SE CREO EL ITEM:" + itemNew.getId();
    }

    @Override
    public List<ItemViewResponseDTO> obtenerItemsDisponiblesDeProducto(Integer idProducto) {
        return itemRepository.findByProductoIdAndFechaVentaIsNull(idProducto).stream()
                .map(this::convertAITemViewResponseDTO)
                .toList();
    }


    @Override
    public String remove(Integer idItem) {
        Item item = itemRepository.findById(idItem).orElseThrow(() -> new NotFoundException("No se encontro el item a borrar , el id: " + idItem));
        itemRepository.delete(item);
        return "SE ELIMINA EL ITEM:" + item.getId();
    }

    @Override
    public List<ItemViewResponseDTO> vencidosHoy() {
        return itemRepository.findByVencimientoLessThanEqual(LocalDate.now()).stream()
                .map(this::convertAITemViewResponseDTO)
                .toList();

    }


    private ItemViewResponseDTO convertAITemViewResponseDTO(Item item) {
        return ItemViewResponseDTO.builder()
                .id(item.getId())
                .vencimiento(item.getVencimiento())
                .elaboracion(item.getElaboracion())
                .fechaVenta(item.getFechaVenta())
                .numeroLote(item.getNumeroLote())
                .producto(ProductoBasicResponseDTO.builder()
                        .id(item.getProducto().getId())
                        .codigoBarras(item.getProducto().getCodigoBarras())
                        .categoria(item.getProducto().getCategoria())
                        .nombre(item.getProducto().getNombre())
                        .marca(item.getProducto().getMarca())
                        .proveedor(item.getProducto().getProveedor())
                        .build())
                .build();
    }

}
