package com.giuseppemarket.controller;


import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.dto.producto.ProductoRequestDTO;
import com.giuseppemarket.service.IItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ItemCrearRequestDTO itemCrearRequestDTO) {
        return ResponseEntity.ok(itemService.crear(itemCrearRequestDTO));
    }

    @GetMapping("/ver_disponibles_de_producto/{idProducto}")
    public ResponseEntity<?> obtenerItemsDisponiblesDeProducto(@PathVariable("idProducto") Integer idProducto) {
        return ResponseEntity.ok(itemService.obtenerItemsDisponiblesDeProducto(idProducto));
    }

    @DeleteMapping("/remove/{idItem}")
    public ResponseEntity<?> delete(@PathVariable("idItem") Integer idItem) {
        return ResponseEntity.ok(itemService.remove(idItem));
    }

}
