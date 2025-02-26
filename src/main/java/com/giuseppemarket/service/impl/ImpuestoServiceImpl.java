package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.impuesto.*;
import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Impuesto;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.ProductoImpuesto;
import com.giuseppemarket.repository.IImpuestoRepository;
import com.giuseppemarket.repository.IProductoImpuestoRepository;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IImpuestoService;
import com.giuseppemarket.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpuestoServiceImpl implements IImpuestoService {
    private final IImpuestoRepository impuestoRepository;
    private final IProductoRepository productoRepository;
    private final IProductoImpuestoRepository productoImpuestoRepository;
    private final IProductoService productoService;


    @Override
    public ImpuestoResponseDTO crear(ImpuestoRequestDTO impuestoRequestDTO) {
        Impuesto impuesto = Impuesto.builder()
                .valor(impuestoRequestDTO.getValor())
                .nombre(impuestoRequestDTO.getNombre())
                .build();
        impuestoRepository.save(impuesto);
        return ImpuestoResponseDTO.builder()
                .valor(impuestoRequestDTO.getValor())
                .nombre(impuestoRequestDTO.getNombre())
                .build();

    }

    @Override
    public ImpuestoResponseDTO update(ImpuestoRequestDTO impuestoRequestDTO, Integer id) {
        Impuesto impuesto = impuestoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el impuesto con el id: " + id));

        impuesto.setValor(impuestoRequestDTO.getValor());
        impuesto.setNombre(impuestoRequestDTO.getNombre());
        impuestoRepository.save(impuesto);
        return ImpuestoResponseDTO.builder()
                .valor(impuestoRequestDTO.getValor())
                .nombre(impuestoRequestDTO.getNombre())
                .build();
    }

    @Override
    public String remove(Integer id) {
        Impuesto impuesto = impuestoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el impuesto con el id: " + id));

        impuestoRepository.delete(impuesto);
        return "REMOVIDO EL IMPUESTO ID: " + id;
    }

    @Override
    public ImpuestoAsignacionResponseDTO asignar(ImpuestoAsignacionRequestDTO impuestoAsignacionRequestDTO) {
        Impuesto impuesto = impuestoRepository
                .findById(impuestoAsignacionRequestDTO.getIdImpuesto())
                .orElseThrow(() -> new NotFoundException("No se encontro el impuesto con el id: " + impuestoAsignacionRequestDTO.getIdImpuesto()));
        Producto producto = productoRepository
                .findById(impuestoAsignacionRequestDTO.getIdProducto())
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " + impuestoAsignacionRequestDTO.getIdProducto()));

        ProductoImpuesto asignacion = ProductoImpuesto.builder()
                .impuesto(impuesto)
                .producto(producto)
                .build();

        productoService.addImpuesto(producto.getId(),impuesto.getValor());
        productoImpuestoRepository.save(asignacion);
        return ImpuestoAsignacionResponseDTO.builder()
                .idAsignacion(asignacion.getId())
                .impuesto(ImpuestoResponseDTO.builder().id(impuesto.getId()).valor(impuesto.getValor()).nombre(impuesto.getNombre()).build())
                .producto(ProductoBasicResponseDTO.builder()
                        .id(producto.getId())
                        .codigoBarras(producto.getCodigoBarras())
                        .categoria(producto.getCategoria())
                        .nombre(producto.getNombre())
                        .marca(producto.getMarca())
                        .proveedor(producto.getProveedor())
                        .build())
                .build();
    }

    @Override
    public String desasignar(ImpuestoDesasignacionRequestDTO impuestoDesasignacionRequestDTO) {
        Producto producto = productoRepository
                .findById(impuestoDesasignacionRequestDTO.getIdProducto())
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " +impuestoDesasignacionRequestDTO.getIdProducto()));
        Impuesto impuesto = impuestoRepository
                .findById(impuestoDesasignacionRequestDTO.getIdImpuesto())
                .orElseThrow(() -> new NotFoundException("No se encontro el impuesto con el id: " +impuestoDesasignacionRequestDTO.getIdImpuesto()));

        ProductoImpuesto productoImpuesto = productoImpuestoRepository.findByProductoIdAndImpuestoId(producto.getId(),impuesto.getId());

        productoService.removeImpuesto(producto.getId(),impuesto.getValor());
        productoImpuestoRepository.delete(productoImpuesto);

        return "impuesto desasignado";
    }


    @Override
    public List<ImpuestoResponseDTO> obtenerAllAsignacionesDeProducto(Integer idProducto) {
        return productoImpuestoRepository.findByProductoId(idProducto).stream()
                .map(this::convertirProductoImpuesto)
                .toList();
    }

    private ImpuestoResponseDTO convertirProductoImpuesto(ProductoImpuesto productoImpuesto) {
        return ImpuestoResponseDTO.builder().id(productoImpuesto.getImpuesto().getId()).valor(productoImpuesto.getImpuesto().getValor()).nombre(productoImpuesto.getImpuesto().getNombre()).build();
    }

}
