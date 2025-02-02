package com.giuseppemarket.controller;

import com.giuseppemarket.dto.producto.ProductoRequestDTO;
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

    @GetMapping("/sucursal")
    public ResponseEntity<?> getProductosSucursal() {
        Sucursal sucursal = getUserFromToken().getSucursal();
        return ResponseEntity.ok(productoService.obtenerProductosBySucursal(sucursal));

    }

    @GetMapping("/obtener_all")
    public ResponseEntity<?> obtenerAll() {
        return ResponseEntity.ok(productoService.obtenerAllProductos());
    }

    @GetMapping("/codigoBarra/{cod}")
    public ResponseEntity<?> getProductoByCodigoBarra(@PathVariable("cod") String codigoBarra) {
        return ResponseEntity.ok(productoService.obtenerProductosByCodigoBarra(codigoBarra));
    }
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<?> getProductosByCategoria(@PathVariable("categoria") String categoria) {
        return ResponseEntity.ok(productoService.obtenerProductosByCategoria(categoria));
    }


    // Epic 2
    // ABM de pruductos

    // obtener enums para crear producto
    @GetMapping("/sucursales")
    public ResponseEntity<?> getSucursales() {
        return ResponseEntity.ok(productoService.obtenerSucursales());
    }
    @GetMapping("/condiciones_productos")
    public ResponseEntity<?> getCondicionesProductos() {
        return ResponseEntity.ok(productoService.obtenerCondicionProducto());
    }
    @GetMapping("/estado")
    public ResponseEntity<?> getestado() {
        return ResponseEntity.ok(productoService.obtenerEstados());
    }
    //crear producto
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody ProductoRequestDTO productoCreateRequestDTO) {
        return ResponseEntity.ok(productoService.crear(productoCreateRequestDTO));
    }

    @PatchMapping("/update/{idProducto}")
    public ResponseEntity<?> update(@RequestBody ProductoRequestDTO productoCreateRequestDTO,@PathVariable("idProducto") Integer idProducto) {
        return ResponseEntity.ok(productoService.update(productoCreateRequestDTO,idProducto));
    }

    @GetMapping("/deficit_stock")
    public ResponseEntity<?> obtenerProductoConDeficitStock() {
        return ResponseEntity.ok(productoService.obtenerProductoEnDeficitStock());
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
