package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.producto.ProductoCreateRequestDTO;
import com.giuseppemarket.dto.producto.ProductoViewByVentaResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.CondicionVenta;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            subtotal += p.getPrecio();
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

    @Override
    public String crear(ProductoCreateRequestDTO productoCreateRequestDTO) {
        // TODO : agregar descuento
        double costo = productoCreateRequestDTO.getCosto();
        double porcentajeGan = productoCreateRequestDTO.getPorcentajeGanancia();

        double precio = costo+ ( ( costo * porcentajeGan)/100);
        double precioConDescuento = precio - ( (precio * productoCreateRequestDTO.getDescuento())/100 );
        double ganancia = precioConDescuento  -  costo ;
        Producto productoNew = Producto.builder()
                .nombre(productoCreateRequestDTO.getNombre())
                .marca(productoCreateRequestDTO.getMarca())
                .descripcion(productoCreateRequestDTO.getDescripcion())
                .costo(productoCreateRequestDTO.getCosto())
                .porcentajeGanancia(productoCreateRequestDTO.getPorcentajeGanancia())
                .descuento(productoCreateRequestDTO.getDescuento())
                .codigoBarras(productoCreateRequestDTO.getCodigoBarras())
                .stockActual(productoCreateRequestDTO.getStockActual())
                .stockMinimo(productoCreateRequestDTO.getStockMinimo())
                .stockMaximo(productoCreateRequestDTO.getStockMaximo())
                .categoria(productoCreateRequestDTO.getCategoria())
                .fabricante(productoCreateRequestDTO.getFabricante())
                .proveedor(productoCreateRequestDTO.getProveedor())
                .estado(productoCreateRequestDTO.getEstado())
                .sucursal(productoCreateRequestDTO.getSucursal())
                .condicionProducto(productoCreateRequestDTO.getCondicionProducto())
                .precio(precioConDescuento)
                .ganancia(ganancia)
                .build();

        productoRepository.save(productoNew);

        return "";
    }


    @Override
    public List<Estado> obtenerEstados() {
        return Arrays.asList(Estado.values());
    }
    @Override
    public List<Sucursal> obtenerSucursales() {
        return Arrays.asList(Sucursal.values());
    }
    @Override
    public List<CondicionProducto> obtenerCondicionProducto() {
        return Arrays.asList(CondicionProducto.values());
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

    @Override
    public String habilitar(Integer idProducto) {
        Producto p = obtenerProductoById(idProducto);
        if(p.getEstado().equals(Estado.INHABILITADO)) {
            p.setEstado(Estado.HABILITADO);
            productoRepository.save(p);
            return "HABILITADO EL PRODUCTO CON EL ID : " + p.getId();
        }else{
            throw new RuntimeException("El producto no tiene estado INHABILITADO");
        }
    }

    @Override
    public String inhabilitar(Integer idProducto) {
        Producto p = obtenerProductoById(idProducto);
        if(p.getEstado().equals(Estado.HABILITADO)) {
            p.setEstado(Estado.INHABILITADO);
            productoRepository.save(p);
            return "INHABILITADO EL PRODUCTO CON EL ID : " + p.getId();
        }else{
            throw new RuntimeException("El producto no tiene estado HABILITADO");
        }

    }

    private Producto obtenerProductoById(Integer id) {
        return productoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con el id: " + id));
    }

}
