package com.giuseppemarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Venta {
    @Id
    @Column(name = "comprobante", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "fechaHora", nullable = false)
    private Instant fechaHora;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "subtotal")
    private Integer subtotal;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "total")
    private Integer total;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;

}