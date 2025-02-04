package com.giuseppemarket.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monto_real")
public class MontoReal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto_total")
    private double montoTotal;

    @Column(name = "monto_tarjetas")
    private double montoTarjetas;

    @Column(name = "monto_transferencias")
    private double montoTransferencias;

    @Column(name = "monto_efectivo")
    private double montoEfectivo;

    @Column(name = "monot_otros")
    private double montoOtros;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

}