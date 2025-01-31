package com.giuseppemarket.controller;


import com.giuseppemarket.dto.login.LoginRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.service.IVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final IVentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody VentaCreateRequestDTO ventaCreateRequestDTO) {
        return ResponseEntity.ok(ventaService.realizarVenta(ventaCreateRequestDTO));
    }
}
