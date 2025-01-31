package com.giuseppemarket.model;

import com.giuseppemarket.utils.enums.CondicionVenta;
import com.giuseppemarket.utils.enums.Sucursal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comprobante", nullable = false)
    private Integer comprobante;

    @NotNull
    @Column(name = "fechaHora", nullable = false)
    private Instant fechaHora;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "subtotal")
    private double subtotal;

    @Column(name = "descuento")
    private double descuento;

    @Column(name = "total")
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_venta", nullable = false)
    private CondicionVenta condicionVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;

    @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Item> items;


}