package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import com.giuseppemarket.dto.venta.*;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IVentaRepository;
import com.giuseppemarket.service.ICajaService;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.service.IVentaService;
import com.giuseppemarket.utils.enums.CondicionVenta;
import com.giuseppemarket.utils.enums.Estado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {
    private final IProductoService productoService;
    private final IVentaRepository ventaRepository;
    private final ICajaService cajaService;
    private final IItemService itemService;


    @Override
    public VentaResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO, Integer idUsuario) {
        //crear venta
        Venta venta = Venta.builder()
                .fechaHora(Instant.now())
                .observaciones(ventaCreateRequestDTO.getObservaciones())
                .condicionVenta(ventaCreateRequestDTO.getCondicionVenta())
                .descuento(ventaCreateRequestDTO.getDescuento())
                .items(new ArrayList<>())
                .build();
        ventaRepository.save(venta);
        //afecta a stock y obtiene subtotal y agrega los items de la venta
        for (Integer idProducto : ventaCreateRequestDTO.getIdproductos()) {
            //TODO : REFACTORIZAR ESTO
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
    public List<VentasPorFechasResponseDTO> historialVentasPorFechas(VentasPorFechasRequestDTO ventasPorFechasRequestDTO) {
        List<Venta> ventas = ventaRepository.findByFechaHoraBetween(ventasPorFechasRequestDTO.getFechaDesde(), ventasPorFechasRequestDTO.getFechaHasta());
        List<VentasPorFechasResponseDTO> ventasPorFechasResponseDTO = new ArrayList<>();
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
            ventasPorFechasResponseDTO.add(VentasPorFechasResponseDTO.builder()
                    .ventaResponseDTO(ventaResponseDTO)
                    .productosResponseDTO(productosResponseDTO)
                    .build());
        }
        return ventasPorFechasResponseDTO;
    }


    // TODO : HACER LOGICA PARA OBTENER TODOS
    //  LOS PRODUCTOS VENDIDOS EN FECHAS Y LA CANTIDAD DE VENTAS
    @Override
    public ProductosVendidosResponseDTO obtenerProductosVendidos(ProductosVendidosRequestDTO productosVendidosRequestDTO) {
        return null;
    }

    private VentaResponseDTO convertirAVentaResponseDTO(Venta venta) {
        return VentaResponseDTO.builder().build();
    }
}
