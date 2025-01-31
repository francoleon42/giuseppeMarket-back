package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.repository.IVentaRepository;
import com.giuseppemarket.service.IAuthService;
import com.giuseppemarket.service.ICajaService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.service.IVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements IVentaService {
    private final IProductoService productoService;
    private final IVentaRepository ventaRepository;
    private final ICajaService cajaService;


    @Override
    public VentaCreateResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO) {
        double subtotal = 0;
        //afecta a stock y obtiene subtotal
        for (Integer idProducto : ventaCreateRequestDTO.getIdproductos()){
            productoService.disminuirStock(idProducto);
            subtotal = productoService.subtotalDeProductos(ventaCreateRequestDTO.getIdproductos());
        }

        // genera la venta
        double total = subtotal - (( subtotal * ventaCreateRequestDTO.getDescuento())/100);
        Venta venta = Venta.builder()
                .fechaHora(Instant.now())
                .observaciones(ventaCreateRequestDTO.getObservaciones())
                .condicionVenta(ventaCreateRequestDTO.getCondicionVenta())
                .subtotal(subtotal)
                .descuento(ventaCreateRequestDTO.getDescuento())
                .total(total)
                .build();

        ventaRepository.save(venta);

        // aafecta a la caja
        cajaService.incrementarCaja(ventaCreateRequestDTO.getIdUsuario(),total);

        return VentaCreateResponseDTO
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
}
