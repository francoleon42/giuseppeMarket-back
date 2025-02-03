package com.giuseppemarket.controller;


import com.giuseppemarket.dto.login.LoginRequestDTO;
import com.giuseppemarket.dto.venta.ProductosVendidosRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.dto.venta.VentasPorFechasRequestDTO;
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


    //
    @GetMapping("/condiciones_venta")
    public ResponseEntity<?> getCondiciones_venta() {
        return ResponseEntity.ok(ventaService.obtenerCondicionesVenta());
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody VentaCreateRequestDTO ventaCreateRequestDTO) {
        Usuario usuario = getUserFromToken();
        return ResponseEntity.ok(ventaService.realizarVenta(ventaCreateRequestDTO, usuario.getId()));
    }

    @GetMapping("/visualizar_productos_vendidos")
    public ResponseEntity<?> visualizarProductosVendidos(@RequestBody ProductosVendidosRequestDTO productosVendidosRequestDTO) {
        return ResponseEntity.ok(ventaService.obtenerProductosVendidos(productosVendidosRequestDTO));
    }
    @GetMapping("/historial_en_fechas")
    public ResponseEntity<?> historialVentasPorFechas(@RequestBody VentasPorFechasRequestDTO ventasPorFechasRequestDTO) {
        return ResponseEntity.ok(ventaService.historialVentasPorFechas(ventasPorFechasRequestDTO));
    }
    @GetMapping("/ver_ventas_de_fecha")
    public ResponseEntity<?> obtenrVentasDeFecha(@RequestBody VentasPorFechasRequestDTO ventasPorFechasRequestDTO) {
        return ResponseEntity.ok(ventaService.historialVentasPorFechas(ventasPorFechasRequestDTO));
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
