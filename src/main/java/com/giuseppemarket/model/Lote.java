package com.giuseppemarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Lote {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @NotNull
    @Column(name = "fechaElaboracion", nullable = false)
    private LocalDate fechaElaboracion;

}