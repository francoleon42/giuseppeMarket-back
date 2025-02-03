package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import com.giuseppemarket.dto.producto.ProductoRequestDTO;
import com.giuseppemarket.dto.producto.ProductoResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.ProductoImpuesto;
import com.giuseppemarket.model.Venta;
import com.giuseppemarket.repository.IProductoRepository;
import com.giuseppemarket.service.IItemService;
import com.giuseppemarket.service.IProductoService;
import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {
    private final IProductoRepository productoRepository;
    private final IItemService itemService;

    @Override
    public Item disminuirStock(Integer idProducto) {
        Producto producto = obtenerProductoById(idProducto);
        if (0 <= producto.getStockActual() - 1) {
            producto.setStockActual(producto.getStockActual() - 1);
            Item item = itemService.venderItem(producto.getId());
            productoRepository.save(producto);
            return item;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para realizar la venta");
        }

    }

    @Override
    public double subtotalDeProductos(List<Integer> idProductos) {
        double subtotal = 0.0;
        for (Integer idProducto : idProductos) {
            Producto p = obtenerProductoById(idProducto);
            subtotal += p.getPrecio();
            productoRepository.save(p);
        }
        return subtotal;
    }

    @Override
    public List<ProductoResponseDTO> obtenerProductosBySucursal(Sucursal sucursal) {
        return productoRepository.findBySucursal(sucursal).stream()
                .map(this::convertToProducto)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> obtenerProductosByCodigoBarra(String codigoBarra) {
        return productoRepository.findByCodigoBarras(codigoBarra).stream()
                .map(this::convertToProducto)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> obtenerProductosByCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoringCase(categoria).stream()
                .map(this::convertToProducto)
                .toList();

    }

    @Override
    public List<ProductoResponseDTO> obtenerAllProductos() {
        return productoRepository.findAll().stream()
                .map(this::convertToProducto)
                .toList();
    }

    @Override
    public ProductoBasicResponseDTO crear(ProductoRequestDTO productoRequestDTO) {

        double costo = productoRequestDTO.getCosto();
        double porcentajeGan = productoRequestDTO.getPorcentajeGanancia();

        double precio = costo + ((costo * porcentajeGan) / 100);
        double precioConDescuento = precio - ((precio * productoRequestDTO.getDescuento()) / 100);
        double ganancia = precioConDescuento - costo;
        Producto productoNow = Producto.builder()
                .nombre(productoRequestDTO.getNombre())
                .marca(productoRequestDTO.getMarca())
                .descripcion(productoRequestDTO.getDescripcion())
                .costo(productoRequestDTO.getCosto())
                .porcentajeGanancia(productoRequestDTO.getPorcentajeGanancia())
                .descuento(productoRequestDTO.getDescuento())
                .codigoBarras(productoRequestDTO.getCodigoBarras())
                .stockMinimo(productoRequestDTO.getStockMinimo())
                .stockMaximo(productoRequestDTO.getStockMaximo())
                .stockActual(0)
                .categoria(productoRequestDTO.getCategoria())
                .fabricante(productoRequestDTO.getFabricante())
                .proveedor(productoRequestDTO.getProveedor())
                .estado(productoRequestDTO.getEstado())
                .sucursal(productoRequestDTO.getSucursal())
                .condicionProducto(productoRequestDTO.getCondicionProducto())
                .precio(precioConDescuento)
                .ganancia(ganancia)
                .build();

        productoRepository.save(productoNow);

        return ProductoBasicResponseDTO.builder()
                .id(productoNow.getId())
                .codigoBarras(productoNow.getCodigoBarras())
                .categoria(productoNow.getCategoria())
                .nombre(productoNow.getNombre())
                .marca(productoNow.getMarca())
                .proveedor(productoNow.getProveedor())
                .build();

    }

    //TODO: revisar el asignar e designar impuestos.
    @Override
    public void addImpuesto(Integer idProducto, double impuesto) {

        Producto producto = obtenerProductoById(idProducto);
        double costoConImpuesto = producto.getCosto() + ((producto.getCosto()*impuesto)/100) ;
        double porcentajeGan = producto.getPorcentajeGanancia();
        double precioConPorcentajeGanancia = costoConImpuesto + ((costoConImpuesto * porcentajeGan) / 100);
        double precio = precioConPorcentajeGanancia - ((precioConPorcentajeGanancia * producto.getDescuento()) / 100);
        double ganancia = precio - costoConImpuesto;

        producto.setPrecio(precio);
        producto.setGanancia(ganancia);
        productoRepository.save(producto);
    }

    @Override
    public void removeImpuesto(Integer idProducto, double impuesto) {
        Producto producto = obtenerProductoById(idProducto);

        // Invertir el cálculo del costo original antes del impuesto
        double costoSinImpuesto = producto.getCosto() - ((producto.getCosto()*impuesto)/100) ;

        double porcentajeGan = producto.getPorcentajeGanancia();
        double precioSinDescuento = costoSinImpuesto + ((costoSinImpuesto * porcentajeGan) / 100);
        double precio = precioSinDescuento - ((precioSinDescuento * producto.getDescuento()) / 100);
        double ganancia = precio - costoSinImpuesto;

        producto.setPrecio(precio);
        producto.setGanancia(ganancia);

        productoRepository.save(producto);
    }


    @Override
    public String update(ProductoRequestDTO productoRequestDTO, Integer idProducto) {
        double costo = productoRequestDTO.getCosto();
        double porcentajeGan = productoRequestDTO.getPorcentajeGanancia();

        double precio = costo + ((costo * porcentajeGan) / 100);
        double precioConDescuento = precio - ((precio * productoRequestDTO.getDescuento()) / 100);
        double ganancia = precioConDescuento - costo;

        Producto p = obtenerProductoById(idProducto);
        p.setNombre(productoRequestDTO.getNombre());
        p.setMarca(productoRequestDTO.getMarca());
        p.setDescripcion(productoRequestDTO.getDescripcion());
        p.setCosto(productoRequestDTO.getCosto());
        p.setPorcentajeGanancia(productoRequestDTO.getPorcentajeGanancia());
        p.setDescuento(productoRequestDTO.getDescuento());
        p.setCodigoBarras(productoRequestDTO.getCodigoBarras());
        p.setStockMinimo(productoRequestDTO.getStockMinimo());
        p.setStockMaximo(productoRequestDTO.getStockMaximo());
        p.setCategoria(productoRequestDTO.getCategoria());
        p.setFabricante(productoRequestDTO.getFabricante());
        p.setProveedor(productoRequestDTO.getProveedor());
        p.setEstado(productoRequestDTO.getEstado());
        //La sucursal no se editara
//        p.setSucursal(productoRequestDTO.getSucursal());
        p.setCondicionProducto(productoRequestDTO.getCondicionProducto());
        p.setPrecio(precioConDescuento);
        p.setGanancia(ganancia);
        productoRepository.save(p);

        return "PRODUCTO ACTUALIZADO";
    }

    @Override
    public List<ProductoResponseDTO> obtenerProductoEnDeficitStock() {
        return productoRepository.findConDeficitStock().stream()
                .map(this::convertToProducto)
                .toList();
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

    private ProductoResponseDTO convertToProducto(Producto producto) {
        return ProductoResponseDTO.builder()
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
