package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.venta.VentaCreateRequestDTO;
import com.giuseppemarket.dto.venta.VentaCreateResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.repository.IVentaRepository;
import com.giuseppemarket.service.IAuthService;
import com.giuseppemarket.service.ICajaService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.service.IVentaService;
import com.giuseppemarket.utils.enums.CondicionVenta;
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


    @Override
    public VentaCreateResponseDTO realizarVenta(VentaCreateRequestDTO ventaCreateRequestDTO,Integer idUsuario) {
        //crear venta
        Venta venta = Venta.builder()
                .fechaHora(Instant.now())
                .observaciones(ventaCreateRequestDTO.getObservaciones())
                .condicionVenta(ventaCreateRequestDTO.getCondicionVenta())
                .descuento(ventaCreateRequestDTO.getDescuento())
                .items(new ArrayList<>())
                .build();

        //afecta a stock y obtiene subtotal y agrega los items de la venta
        for (Integer idProducto : ventaCreateRequestDTO.getIdproductos()){
            Item item = productoService.disminuirStock(idProducto);
            venta.getItems().add(item);
        }
        double subtotal = productoService.subtotalDeProductos(ventaCreateRequestDTO.getIdproductos());
        double total = subtotal - (( subtotal * ventaCreateRequestDTO.getDescuento())/100);
        venta.setSubtotal(subtotal);
        venta.setTotal(total);
        ventaRepository.save(venta);

        // aafecta a la caja
        cajaService.incrementarCaja(idUsuario,total);

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

    @Override
    public List<CondicionVenta> obtenerCondicionesVenta() {
        return Arrays.asList(CondicionVenta.values());
    }
}
