package com.giuseppemarket.config;


import com.giuseppemarket.model.*;
import com.giuseppemarket.repository.*;
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
                .descripcion("Laptop de alto rendimiento")
                .costo(800.0f)
                .porcentajeGanancia(20.0)
                .descuento(0.0)
                .estado(Estado.INHABILITADO)
                .codigoBarras("1234567890123")
                .stockActual(5)
                .stockMinimo(5)
                .stockMaximo(20)
                .categoria("Electrónica")
                .fabricante("Dell Inc.")
                .proveedor("Proveedor XYZ")
                .sucursal(Sucursal.SUCURSAL_1)
                .condicionProducto(CondicionProducto.MINORISTA)
                .build();

        double precio1 = producto1.getCosto()+ ( ( producto1.getCosto() * producto1.getPorcentajeGanancia())/100);
        double precio1Des= precio1 - ((precio1 * producto1.getDescuento())/100) ;
        double ganancia1 = precio1Des  -  producto1.getCosto() ;
        producto1.setPrecio(precio1Des);
        producto1.setGanancia(ganancia1);

        Producto producto2 = Producto.builder()
                .nombre("Smartphone Samsung")
                .marca("Samsung")
                .descripcion("Teléfono inteligente de última generación")
                .costo(500.0f)
                .porcentajeGanancia(25.0)
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

        double precio2 = producto2.getCosto()+ ( ( producto2.getCosto() * producto2.getPorcentajeGanancia())/100);
        double precio2Des= precio2 - ((precio2 * producto2.getDescuento())/100) ;
        double ganancia2 = precio2Des  -  producto2.getCosto() ;
        producto2.setPrecio(precio2Des);
        producto2.setGanancia(ganancia2);

        Producto producto3 = Producto.builder()
                .nombre("Monitor LG 24")
                .marca("LG")
                .descripcion("Monitor Full HD de 24 pulgadas")
                .costo(200.0f)
                .porcentajeGanancia(30.0)
                .descuento(10.0)
                .estado(Estado.HABILITADO)
                .codigoBarras("4567891234567")
                .stockActual(15)
                .stockMinimo(10)
                .stockMaximo(15)
                .categoria("Periféricos")
                .fabricante("LG Electronics")
                .proveedor("Proveedor DEF")
                .sucursal(Sucursal.SUCURSAL_1)
                .condicionProducto(CondicionProducto.MAYORISTA)
                .build();


        double precio3 = producto3.getCosto() + ( ( producto3.getCosto() * producto3.getPorcentajeGanancia())/100);
        double precio3Des= precio3 - ((precio3 * producto3.getDescuento())/100) ;
        double ganancia3 = precio3Des  -  producto3.getCosto() ;
        producto3.setPrecio(precio3Des);
        producto3.setGanancia(ganancia3);

        productoRepository.saveAll(List.of(producto1, producto2, producto3));

        // Crear impuestos
        Impuesto iva = Impuesto.builder()
                .valor(21)
                .nombre("IVA 21%")
                .build();

        impuestoRepository.save(iva);

        // Asociar productos con impuesto
        productoImpuestoRepository.saveAll(List.of(
                ProductoImpuesto.builder().producto(producto1).impuesto(iva).build(),
                ProductoImpuesto.builder().producto(producto2).impuesto(iva).build(),
                ProductoImpuesto.builder().producto(producto3).impuesto(iva).build()
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


        // ABRIR CAJA
        Caja caja = Caja.builder()
                .apertura(Instant.now())
                .montoInicial(1000.0)
                .observaciones("Apertura inicial de caja")
                .usuario(vendedor)
                .montoReal(null)
                .build();


        cajaRepository.save(caja);

    }
}




