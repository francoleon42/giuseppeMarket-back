package com.giuseppemarket.controller;

import com.giuseppemarket.dto.impuesto.ImpuestoRequestDTO;
import com.giuseppemarket.dto.producto.ProductoRequestDTO;
import com.giuseppemarket.service.IImpuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/impuesto")
@RequiredArgsConstructor
public class ImpuestoController {

    private final IImpuestoService impuestoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ImpuestoRequestDTO impuestoRequestDTO) {
        return ResponseEntity.ok(impuestoService.crear(impuestoRequestDTO));
    }

    @PatchMapping("/update/{idImpuesto}")
    public ResponseEntity<?> update(@RequestBody ImpuestoRequestDTO impuestoRequestDTO
            ,@PathVariable("idImpuesto") Integer idImpuesto) {
        return ResponseEntity.ok(impuestoService.update(impuestoRequestDTO,idImpuesto));
    }


}
