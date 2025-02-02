package com.giuseppemarket.dto.item;

import com.giuseppemarket.dto.producto.ProductoBasicResponseDTO;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Venta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemViewResponseDTO {

    private Integer id;
    private LocalDate vencimiento;
    private LocalDate elaboracion;
    private LocalDate fechaVenta;
    private Integer numeroLote;
    private ProductoBasicResponseDTO producto;
}
