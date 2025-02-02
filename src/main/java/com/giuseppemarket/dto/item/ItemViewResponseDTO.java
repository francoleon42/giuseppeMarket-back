package com.giuseppemarket.dto.item;

import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Venta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

public class ItemViewResponseDTO {

    private Integer id;
    private LocalDate vencimiento;
    private LocalDate elaboracion;
    private LocalDate fechaVenta;
    private Integer numeroLote;
    private Venta venta;
    private Producto producto;
}
