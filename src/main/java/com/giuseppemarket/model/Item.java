package com.giuseppemarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "vencimiento")
    private LocalDate vencimiento;

    @Column(name = "elaboracion")
    private LocalDate elaboracion;

    @Column(name = "fechaVenta")
    private LocalDate fechaVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idLote")
    private Lote idLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idVenta")
    private Venta idVenta;

}