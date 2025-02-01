package com.giuseppemarket.controller;


import com.giuseppemarket.dto.login.LoginRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.exception.BadRoleException;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.service.IVentaService;
import com.giuseppemarket.utils.enums.Rol;
import com.giuseppemarket.utils.enums.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final IVentaService ventaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody VentaCreateRequestDTO ventaCreateRequestDTO) {
        Usuario usuario = getUserFromToken();
        return ResponseEntity.ok(ventaService.realizarVenta(ventaCreateRequestDTO,usuario.getId()));
    }


    // TODO : ver requerimiento
    //  - 1.3 Visualizar ventas totales por sumatoria
    //  - 1.4 Visualizar ventas
    @GetMapping("/ventas_totales_by_fecha")
    public ResponseEntity<?> ventasTotalesPorSumatoria(@RequestBody VentaCreateRequestDTO ventaCreateRequestDTO) {
        return ResponseEntity.ok(ventaService.obtenerCondicionesVenta());
    }
    @GetMapping("/condiciones_venta")
    public ResponseEntity<?> getCondiciones_venta() {
        return ResponseEntity.ok(ventaService.obtenerCondicionesVenta());
    }

    private Usuario getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication != null && authentication.isAuthenticated())) {
            throw new NotFoundException("El token no corresponde a un usuario.");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario.getRol() != Rol.VENDEDOR) {
            throw new BadRoleException("El usuario no corresponde a un vendedor.");
        }
        return usuario;
    }
}
