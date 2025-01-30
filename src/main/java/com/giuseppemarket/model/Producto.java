package com.giuseppemarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Producto {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Size(max = 255)
    @Column(name = "marca")
    private String marca;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "costo")
    private Float costo;

    @Column(name = "porcentajeGanancia")
    private Float porcentajeGanancia;

    @Column(name = "ganancia")
    private Float ganancia;

    @Column(name = "precio")
    private Float precio;

    @Column(name = "descuento")
    private Float descuento;

    @Column(name = "habilitado")
    private Boolean habilitado;

    @Size(max = 50)
    @Column(name = "codigoBarras", length = 50)
    private String codigoBarras;

    @Column(name = "stockActual")
    private Integer stockActual;

    @Column(name = "stockMinimo")
    private Integer stockMinimo;

    @Column(name = "stockMaximo")
    private Integer stockMaximo;

    @Size(max = 100)
    @Column(name = "categoria", length = 100)
    private String categoria;

    @Size(max = 255)
    @Column(name = "fabricante")
    private String fabricante;

    @Size(max = 255)
    @Column(name = "proveedor")
    private String proveedor;

    @Lob
    @Column(name = "condicion")
    private String condicion;

    @Lob
    @Column(name = "sucursal")
    private String sucursal;

}