package com.giuseppemarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "caja")
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "apertura", nullable = false)
    private Instant apertura;

    @Column(name = "cierre")
    private Instant cierre;

    @Column(name = "montoInicial")
    private Integer montoInicial;

    @Column(name = "montoFinal")
    private Integer montoFinal;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "monto_real")
    private MontoReal montoReal;

}