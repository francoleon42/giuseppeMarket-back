package com.giuseppemarket.controller;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.exception.BadRoleException;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.utils.enums.Rol;
import com.giuseppemarket.utils.enums.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final IProductoService productoService;

    //TODO: obtener productus de la sucursal del vendor
    @GetMapping("/sucursal")
    public ResponseEntity<?> getProductosSucursal() {
        Sucursal sucursal = getUserFromToken().getSucursal();
        return ResponseEntity.ok(productoService.obtenerProductosBySucursal(sucursal));

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
