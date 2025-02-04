package com.giuseppemarket.config;


import com.giuseppemarket.dto.item.ItemCrearRequestDTO;
import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import com.giuseppemarket.dto.producto.ProductoRequestDTO;
import com.giuseppemarket.model.*;
import com.giuseppemarket.repository.*;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.utils.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class Bootstrap implements ApplicationRunner {
    private final IUsuarioRepository userRepository;

    private final IProductoRepository productoRepository;
    private final IImpuestoRepository impuestoRepository;
    private final IProductoImpuestoRepository productoImpuestoRepository;
    private final IItemRepository itemRepository;
    private final ICajaRepository cajaRepository;

    private final IProductoService productoService;
    private final IItemService itemService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear usuarios con builder
        Usuario admin = Usuario.builder()
                .usuario("admin")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .estadoUsuario(Estado.HABILITADO)
                .rol(Rol.ADMINISTRADOR)
                .nombre("FrancoAdmin")
                .sucursal(Sucursal.SUCURSAL_1)
                .codVerificacionLogin("1")
                .build();

        Usuario vendedor = Usuario.builder()
                .usuario("vendedor")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .estadoUsuario(Estado.HABILITADO)
                .rol(Rol.VENDEDOR)
                .nombre("FrancoVendedor")
                .sucursal(Sucursal.SUCURSAL_1)
                .codVerificacionLogin("1")
                .build();


        userRepository.saveAll(List.of(admin, vendedor));


        //////////
        // Crear productos
        ProductoRequestDTO productoRequestDTO1 = ProductoRequestDTO.builder()
                .nombre("Laptop Dell")
                .marca("Dell")
                .descripcion("Laptop de alto rendimiento")
                .costo(800)
                .porcentajeGanancia(20)
                .descuento(0)
                .codigoBarras("1234567890123")
                .stockMinimo(5)
                .stockMaximo(20)
                .categoria("Electrónica")
                .fabricante("Dell Inc.")
                .proveedor("Proveedor XYZ")
                .estado(Estado.INHABILITADO)
                .sucursal(Sucursal.SUCURSAL_1)
                .condicionProducto(CondicionProducto.MINORISTA)
                .build();
        ProductoBasicResponseDTO productoBasicResponseDTO1 = productoService.crear(productoRequestDTO1);

        ProductoRequestDTO productoRequestDTO2 = ProductoRequestDTO.builder()
                .nombre("Smartphone Samsung")
                .marca("Samsung")
                .descripcion("Teléfono inteligente")
                .costo(500)
                .porcentajeGanancia(25)
                .descuento(10)
                .codigoBarras("9876543210987")
                .stockMinimo(3)
                .stockMaximo(30)
                .categoria("Telefonía")
                .fabricante("Samsung Electronics")
                .proveedor("Proveedor ABC")
                .estado(Estado.HABILITADO)
                .sucursal(Sucursal.SUCURSAL_2)
                .condicionProducto(CondicionProducto.MAYORISTA)
                .build();
        ProductoBasicResponseDTO productoBasicResponseDTO2= productoService.crear(productoRequestDTO2);

        ProductoRequestDTO productoRequestDTO3 = ProductoRequestDTO.builder()
                .nombre("Monitor LG 24")
                .marca("LG")
                .descripcion("Monitor Full HD")
                .costo(200)
                .porcentajeGanancia(30)
                .descuento(10)
                .codigoBarras("4567891234567")
                .stockMinimo(10)
                .stockMaximo(15)
                .categoria("Periféricos")
                .fabricante("LG Electronics")
                .proveedor("Proveedor DEF")
                .estado(Estado.HABILITADO)
                .sucursal(Sucursal.SUCURSAL_1)
                .condicionProducto(CondicionProducto.MAYORISTA)
                .build();
        ProductoBasicResponseDTO productoBasicResponseDTO3= productoService.crear(productoRequestDTO3);

        Producto producto1 = productoRepository.findById(productoBasicResponseDTO1.getId()).orElseThrow();
        Producto producto2 = productoRepository.findById(productoBasicResponseDTO2.getId()).orElseThrow();
        Producto producto3 = productoRepository.findById(productoBasicResponseDTO3.getId()).orElseThrow();


        // Crear impuestos
        Impuesto iva = Impuesto.builder()
                .valor(21)
                .nombre("IVA 21%")
                .build();

        impuestoRepository.save(iva);

        // Asociar productos con impuesto
        productoImpuestoRepository.saveAll(List.of(
                ProductoImpuesto.builder().producto(producto1).impuesto(iva).build(),
                ProductoImpuesto.builder().producto(producto3).impuesto(iva).build()
        ));

        // Crear 5 items para cada producto
        for (Producto producto : List.of(producto1, producto2, producto3)) {
            for (int i = 1; i <= 5; i++) {
                LocalDate fechaVencimiento;
                if (i == 1) {
                    fechaVencimiento = LocalDate.now().minusDays(1); // Ayer
                } else {
                    fechaVencimiento = LocalDate.now().plusMonths(6 + i);
                }

                ItemCrearRequestDTO itemRequest = ItemCrearRequestDTO.builder()
                        .vencimiento(fechaVencimiento)
                        .elaboracion(LocalDate.now().minusDays(10 * i))
                        .numeroLote(1000 + (producto.getNombre().hashCode() % 100) + i)
                        .idProducto(producto.getId()) // Asegúrate de que producto tiene un ID válido
                        .build();

                itemService.crear(itemRequest);
            }
        }




//        // ABRIR CAJA
//        Caja caja = Caja.builder()
//                .apertura(Instant.now())
//                .montoInicial(1000.0)
//                .observaciones("Apertura inicial de caja")
//                .usuario(vendedor)
//                .montoReal(null)
//                .build();
//
//
//        cajaRepository.save(caja);

    }
}




