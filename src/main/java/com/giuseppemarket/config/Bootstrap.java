package com.giuseppemarket.config;


import com.giuseppemarket.model.*;
import com.giuseppemarket.repository.*;
import com.giuseppemarket.utils.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
        Producto producto1 = Producto.builder()
                .nombre("Laptop Dell")
                .marca("Dell")
                .cantidad(10)
                .descripcion("Laptop de alto rendimiento")
                .costo(800.0f)
                .porcentajeGanancia(20.0)
                .ganancia(160.0)
                .precio(960.0)
                .descuento(0.0)
                .estado(Estado.HABILITADO)
                .codigoBarras("1234567890123")
                .stockActual(10)
                .stockMinimo(2)
                .stockMaximo(20)
                .categoria("Electrónica")
                .fabricante("Dell Inc.")
                .proveedor("Proveedor XYZ")
                .sucursal(Sucursal.SUCURSAL_1)
                .condicionProducto(CondicionProducto.MINORISTA)
                .build();

        Producto producto2 = Producto.builder()
                .nombre("Smartphone Samsung")
                .marca("Samsung")
                .cantidad(15)
                .descripcion("Teléfono inteligente de última generación")
                .costo(500.0f)
                .porcentajeGanancia(25.0)
                .ganancia(125.0)
                .precio(625.0)
                .descuento(10.0)
                .estado(Estado.HABILITADO)
                .codigoBarras("9876543210987")
                .stockActual(15)
                .stockMinimo(3)
                .stockMaximo(30)
                .categoria("Telefonía")
                .fabricante("Samsung Electronics")
                .proveedor("Proveedor ABC")
                .sucursal(Sucursal.SUCURSAL_2)
                .condicionProducto(CondicionProducto.MAYORISTA)
                .build();

        Producto producto3 = Producto.builder()
                .nombre("Monitor LG 24")
                .marca("LG")
                .cantidad(8)
                .descripcion("Monitor Full HD de 24 pulgadas")
                .costo(200.0f)
                .porcentajeGanancia(30.0)
                .ganancia(60.0)
                .precio(260.0)
                .descuento(5.0)
                .estado(Estado.HABILITADO)
                .codigoBarras("4567891234567")
                .stockActual(8)
                .stockMinimo(2)
                .stockMaximo(15)
                .categoria("Periféricos")
                .fabricante("LG Electronics")
                .proveedor("Proveedor DEF")
                .sucursal(Sucursal.SUCURSAL_1)
                .condicionProducto(CondicionProducto.MAYORISTA)
                .build();

        productoRepository.saveAll(List.of(producto1, producto2, producto3));

        // Crear impuestos
        Impuesto iva = Impuesto.builder()
                .valor(21)
                .nombre("IVA 21%")
                .build();

        impuestoRepository.save(iva);

        // Asociar productos con impuesto
        productoImpuestoRepository.saveAll(List.of(
                ProductoImpuesto.builder().idProducto(producto1).idImpuesto(iva).build(),
                ProductoImpuesto.builder().idProducto(producto2).idImpuesto(iva).build(),
                ProductoImpuesto.builder().idProducto(producto3).idImpuesto(iva).build()
        ));

        // Crear 5 items para cada producto
        for (Producto producto : List.of(producto1, producto2, producto3)) {
            for (int i = 1; i <= 5; i++) {
                itemRepository.save(Item.builder()
                        .vencimiento(LocalDate.now().plusMonths(6 + i))
                        .elaboracion(LocalDate.now().minusDays(10 * i))
                        .numeroLote(1000 + (producto.getNombre().hashCode() % 100) + i)
                        .producto(producto)
                        .build());
            }
        }

    }
}




