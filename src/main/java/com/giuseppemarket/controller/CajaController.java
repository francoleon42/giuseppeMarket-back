package com.giuseppemarket.controller;

import com.giuseppemarket.dto.caja.CajaAperturaRequestDTO;
import com.giuseppemarket.dto.caja.CajaCerrarRequestDTO;
import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.exception.BadRoleException;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.service.ICajaService;
import com.giuseppemarket.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caja")
@RequiredArgsConstructor
public class CajaController {
    private final ICajaService cajaService;


    @PostMapping("/apertura")
    public ResponseEntity<?> apertura(@RequestBody CajaAperturaRequestDTO cajaAperturaRequestDTO) {
        Usuario usuario = getUserFromToken();
        return ResponseEntity.ok(cajaService.aperturaCaja(usuario, cajaAperturaRequestDTO));
    }

    @PatchMapping("/cierre")
    public ResponseEntity<?> cierre(@RequestBody CajaCerrarRequestDTO cajaCerrarRequestDTO) {
        Usuario usuario = getUserFromToken();
        return ResponseEntity.ok(cajaService.cerrarCaja(usuario, cajaCerrarRequestDTO));
    }

    //
//
//    @GetMapping("/ver_disponibles_de_producto/{idProducto}")
//    public ResponseEntity<?> obtenerItemsDisponiblesDeProducto(@PathVariable("idProducto") Integer idProducto) {
//        return ResponseEntity.ok(itemService.obtenerItemsDisponiblesDeProducto(idProducto));
//    }
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
