package com.giuseppemarket.model;

import com.giuseppemarket.utils.enums.CondicionProducto;
import com.giuseppemarket.utils.enums.Estado;
import com.giuseppemarket.utils.enums.Sucursal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 255)
    @Column(name = "marca")
    private String marca;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "costo")
    private double costo;

    @Column(name = "porcentajeGanancia")
    private double porcentajeGanancia;

    @Column(name = "ganancia")
    private double ganancia;

    @Column(name = "precio")
    private double precio;

    @Column(name = "descuento")
    private double descuento;



    @Column(name = "codigoBarras", length = 50)
    private String codigoBarras;

    @Column(name = "stockActual")
    private Integer stockActual;

    @Column(name = "stockMinimo")
    private Integer stockMinimo;

    @Column(name = "stockMaximo")
    private Integer stockMaximo;


    @Column(name = "categoria", length = 100)
    private String categoria;


    @Column(name = "fabricante")
    private String fabricante;


    @Column(name = "proveedor")
    private String proveedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "sucursal", nullable = false)
    private Sucursal sucursal;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_producto", nullable = false)
    private CondicionProducto condicionProducto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

}