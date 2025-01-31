package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.repository.IVentaRepository;
import com.giuseppemarket.service.IAuthService;
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


    @Override
    public String realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO) {
        double subtotal = 0;
        //afecta a stock
        for (Integer idProducto : ventaCreateRequestDTO.getIdproductos()){
            productoService.disminuirStock(idProducto);
            // obtener subtotal de la venta
            //
        }
        // genera la venta
        double total = subtotal - ventaCreateRequestDTO.getDescuento();
        Venta venta = Venta.builder()
                .fechaHora(Instant.now())
                .observaciones(ventaCreateRequestDTO.getObservaciones())
                .condicionVenta(ventaCreateRequestDTO.getCondicionVenta())
                .subtotal(subtotal)
                .descuento(ventaCreateRequestDTO.getDescuento())
                .total(total)
                .build();

        // aafecta a la caja
        // cajaServicioAumetentar
        return "";
    }
}
