package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import com.giuseppemarket.dto.venta.*;
import com.giuseppemarket.model.Caja;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.repository.IVentaRepository;
import com.giuseppemarket.service.ICajaService;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.service.IVentaService;
import com.giuseppemarket.utils.enums.CondicionVenta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {
    private final IProductoService productoService;
    private final IVentaRepository ventaRepository;
    private final ICajaService cajaService;
    private final IItemService itemService;
    private final IProductoRepository productoRepository;

    private void validarStockDisponible(List<Integer> idsProductos) {
        // 1️⃣ PRIMERA PASADA: Validar stock de todos los productos antes de modificar
        for (Integer idProducto : idsProductos) {
            int cantidadSolicitada = Collections.frequency(idsProductos, idProducto);
            Producto producto = productoRepository.findById(idProducto).orElseThrow();
            if (producto.getStockActual() <= cantidadSolicitada) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No hay stock suficiente para el producto: " + producto.getNombre());
            }
        }
    }

    private void validarSiUsuarioTieneAbiertaCaja(Integer idUsuario) {
        Caja caja = cajaService.obtenerCajaActualByUser(idUsuario);
        if (caja == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para realizar una venta primero debe abrir una caja");
        }
    }

    @Override
    public VentaResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO, Integer idUsuario) {
        validarStockDisponible(ventaCreateRequestDTO.getIdproductos());
        validarSiUsuarioTieneAbiertaCaja(idUsuario);
        //crear venta
        Venta venta = Venta.builder()
                .fechaHora(Instant.now())
                .observaciones(ventaCreateRequestDTO.getObservaciones())
                .condicionVenta(ventaCreateRequestDTO.getCondicionVenta())
                .descuento(ventaCreateRequestDTO.getDescuento())
                .items(new ArrayList<>())
                .build();
        ventaRepository.save(venta);

        for (Integer idProducto : ventaCreateRequestDTO.getIdproductos()) {
            Item item = productoService.disminuirStock(idProducto);
            item.setVenta(venta);
            venta.getItems().add(item);
        }


        double subtotal = productoService.subtotalDeProductos(ventaCreateRequestDTO.getIdproductos());
        double total = subtotal - ((subtotal * ventaCreateRequestDTO.getDescuento()) / 100);
        venta.setSubtotal(subtotal);
        venta.setTotal(total);
        ventaRepository.save(venta);

        // aafecta a la caja
        cajaService.incrementarCaja(idUsuario, total);

        return VentaResponseDTO
                .builder()
                .comprobante(venta.getComprobante())
                .fechaHora(venta.getFechaHora())
                .observaciones(venta.getObservaciones())
                .subtotal(venta.getSubtotal())
                .descuento(venta.getDescuento())
                .total(venta.getTotal())
                .condicionVenta(venta.getCondicionVenta().toString())
                .build();
    }

    @Override
    public List<CondicionVenta> obtenerCondicionesVenta() {
        return Arrays.asList(CondicionVenta.values());
    }

    @Override
    public List<VentaHistorialResponseDTO> historialVentasPorFechas(VentaHistorialRequestDTO ventaHistorialRequestDTO) {
        List<Venta> ventas = ventaRepository.findByFechaHoraBetween(ventaHistorialRequestDTO.getFechaDesde(), ventaHistorialRequestDTO.getFechaHasta());
        return obtenerHistorialDeVenta(ventas);
    }

    @Override
    public List<VentaHistorialResponseDTO> obtenerVentasDeFecha(VentaPorFechaRequestDTO ventaPorFechaRequestDTO) {
        Instant fechaInicio = ventaPorFechaRequestDTO.getFecha().atStartOfDay().toInstant(ZoneOffset.UTC);  // Inicio del día (00:00)
        Instant fechaFin = ventaPorFechaRequestDTO.getFecha().plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);  // Fin del día (23:59:59)
        List<Venta> ventas = ventaRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
        return obtenerHistorialDeVenta(ventas);
    }

    @Override
    public List<ProductosVendidosResponseDTO> obtenerProductosVendidos(ProductosVendidosRequestDTO productosVendidosRequestDTO) {
        List<Venta> ventas = ventaRepository.findByFechaHoraBetween(
                productosVendidosRequestDTO.getFechaDesde(),
                productosVendidosRequestDTO.getFechaHasta()
        );

        Map<Integer, ProductosVendidosResponseDTO> productosVendidosMap = new HashMap<>();

        for (Venta venta : ventas) {
            for (Item item : venta.getItems()) {
                Integer productoId = item.getProducto().getId();
                productosVendidosMap.putIfAbsent(productoId, ProductosVendidosResponseDTO.builder()
                        .productoResponseDTO(
                                ProductoResponseDTO.builder()
                                        .id(productoId)
                                        .nombre(item.getProducto().getNombre())
                                        .marca(item.getProducto().getMarca())
                                        .descripcion(item.getProducto().getDescripcion())
                                        .costo(item.getProducto().getCosto())
                                        .categoria(item.getProducto().getCategoria())
                                        .sucursal(item.getProducto().getSucursal().toString())
                                        .codigoBarras(item.getProducto().getCodigoBarras())
                                        .condicionProducto(item.getProducto().getCondicionProducto().toString())
                                        .stockActual(item.getProducto().getStockActual())
                                        .stockMinimo(item.getProducto().getStockMinimo())
                                        .stockMaximo(item.getProducto().getStockMaximo())
                                        .porcentajeGanancia(item.getProducto().getPorcentajeGanancia())
                                        .ganancia(item.getProducto().getGanancia())
                                        .precio(item.getProducto().getPrecio())
                                        .descuento(item.getProducto().getDescuento())
                                        .estado(item.getProducto().getEstado())
                                        .fabricante(item.getProducto().getFabricante())
                                        .proveedor(item.getProducto().getProveedor())
                                        .build())
                        .cantidadDeVentas(0)
                        .build());

                // Sumar la cantidad vendida
                ProductosVendidosResponseDTO productosVendidosResponseDTO = productosVendidosMap.get(productoId);
                productosVendidosResponseDTO.setCantidadDeVentas(productosVendidosResponseDTO.getCantidadDeVentas() + 1);
            }
        }
        return new ArrayList<>(productosVendidosMap.values());
    }


    private List<VentaHistorialResponseDTO> obtenerHistorialDeVenta(List<Venta> ventas) {
        List<VentaHistorialResponseDTO> ventaHistorialResponseDTO = new ArrayList<>();
        for (Venta venta : ventas) {
            VentaResponseDTO ventaResponseDTO = VentaResponseDTO.builder().build();
            ventaResponseDTO.setComprobante(venta.getComprobante());
            ventaResponseDTO.setFechaHora(venta.getFechaHora());
            ventaResponseDTO.setObservaciones(venta.getObservaciones());
            ventaResponseDTO.setSubtotal(venta.getSubtotal());
            ventaResponseDTO.setDescuento(venta.getDescuento());
            ventaResponseDTO.setTotal(venta.getTotal());
            ventaResponseDTO.setCondicionVenta(venta.getCondicionVenta().toString());
            List<ProductoResponseDTO> productosResponseDTO = new ArrayList<>();
            for (Item item : venta.getItems()) {
                ProductoResponseDTO productoResponseDTO = ProductoResponseDTO.builder()
                        .id(item.getProducto().getId())
                        .nombre(item.getProducto().getNombre())
                        .marca(item.getProducto().getMarca())
                        .descripcion(item.getProducto().getDescripcion())
                        .costo(item.getProducto().getCosto())
                        .categoria(item.getProducto().getCategoria())
                        .sucursal(item.getProducto().getSucursal().toString())
                        .codigoBarras(item.getProducto().getCodigoBarras())
                        .condicionProducto(item.getProducto().getCondicionProducto().toString())
                        .stockActual(item.getProducto().getStockActual())
                        .stockMinimo(item.getProducto().getStockMinimo())
                        .stockMaximo(item.getProducto().getStockMaximo())
                        .porcentajeGanancia(item.getProducto().getPorcentajeGanancia())
                        .ganancia(item.getProducto().getGanancia())
                        .precio(item.getProducto().getPrecio())
                        .descuento(item.getProducto().getPrecio())
                        .estado(item.getProducto().getEstado())
                        .fabricante(item.getProducto().getFabricante())
                        .proveedor(item.getProducto().getProveedor())
                        .build();
                productosResponseDTO.add(productoResponseDTO);
            }
            ventaHistorialResponseDTO.add(VentaHistorialResponseDTO.builder()
                    .ventaResponseDTO(ventaResponseDTO)
                    .productosResponseDTO(productosResponseDTO)
                    .build());
        }
        return ventaHistorialResponseDTO;


    }

    private VentaResponseDTO convertirAVentaResponseDTO(Venta venta) {
        return VentaResponseDTO.builder().build();
    }
}
