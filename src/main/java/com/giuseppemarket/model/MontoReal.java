package com.giuseppemarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MontoReal {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total")
    private Integer total;

    @Column(name = "tarjetas")
    private Integer tarjetas;

    @Column(name = "transferencias")
    private Integer transferencias;

    @Column(name = "efectivo")
    private Integer efectivo;

    @Column(name = "otros")
    private Integer otros;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

}