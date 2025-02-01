package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.ProductoViewByVentaResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
    private final IProductoRepository productoRepository;
    private final IItemService itemService;

    @Override
    public void disminuirStock(Integer idProducto) {
        Producto producto = obtenerProductoById(idProducto);
        if (producto.getStockMinimo() < producto.getStockActual() - 1) {
            producto.setStockActual(producto.getStockActual() - 1);
            itemService.venderItem(producto.getId());
        }
        productoRepository.save(producto);

    }

    @Override
    public double subtotalDeProductos(List<Integer> idProductos) {
        //TODO: revisar si asi se obtiene el subtotal.

        double subtotal = 0.0;
        for (Integer idProducto : idProductos) {
            Producto p = obtenerProductoById(idProducto);
            double costo = p.getCosto(); // lo ingresa el usuarioS
            double gananciaPorcentaje = p.getPorcentajeGanancia(); // lo ingresa el usuarioS
            double precio = costo+((costo * gananciaPorcentaje)/100);
            double ganacia = precio  - costo ;
            subtotal += precio;

            p.setPrecio(precio);
            p.setGanancia(ganacia);
            productoRepository.save(p);
        }
        return subtotal;
    }

    @Override
    public List<ProductoViewByVentaResponseDTO> obtenerProductosBySucursal(Sucursal sucursal) {
        return productoRepository.findBySucursal(sucursal).stream()
                .map(this::convertToProducto)
                .toList();
    }

    @Override
    public List<ProductoViewByVentaResponseDTO> obtenerProductosByCodigoBarra(String codigoBarra) {
        return productoRepository.findByCodigoBarras(codigoBarra).stream()
                .map(this::convertToProducto)
                .toList();
    }

    @Override
    public List<ProductoViewByVentaResponseDTO> obtenerProductosByCategoria(String categoria) {
        return productoRepository.findByCategoriaContaining(categoria).stream()
                .map(this::convertToProducto)
                .toList();

    }

    private ProductoViewByVentaResponseDTO convertToProducto(Producto producto) {
        return ProductoViewByVentaResponseDTO.builder()
                .id(producto.getId())
                .categoria(producto.getCategoria())
                .codigoBarras(producto.getCodigoBarras())
                .nombre(producto.getNombre())
                .marca(producto.getMarca())
                .descripcion(producto.getDescripcion())
                .sucursal(producto.getSucursal().toString())
                .condicionProducto(producto.getCondicionProducto().toString())
                .stockActual(producto.getStockActual())
                .stockMinimo(producto.getStockMinimo())
                .stockMaximo(producto.getStockMaximo())
                .costo(producto.getCosto())
                .porcentajeGanancia(producto.getPorcentajeGanancia())
                .ganancia(producto.getGanancia())
                .precio(producto.getPrecio())
                .descuento(producto.getDescuento())
                .estado(producto.getEstado())
                .fabricante(producto.getFabricante())
                .proveedor(producto.getProveedor())
                .build();
    }

    private Producto obtenerProductoById(Integer id) {
        return productoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " + id));
    }
}
