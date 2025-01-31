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
    private Integer montoTotal;

    @Column(name = "monto_tarjetas")
    private Integer montoTarjetas;

    @Column(name = "monto_transferencias")
    private Integer montoTransferencias;

    @Column(name = "monto_efectivo")
    private Integer montoEfectivo;

    @Column(name = "monot_otros")
    private Integer montoOtros;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

}