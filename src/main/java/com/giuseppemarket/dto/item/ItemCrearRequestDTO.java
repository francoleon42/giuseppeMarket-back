package com.giuseppemarket.dto.item;

import com.giuseppemarket.model.Producto;
import com.giuseppemarket.model.Venta;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ItemCrearRequestDTO {
    LocalDate vencimiento;
    LocalDate elaboracion;
    Integer numeroLote;
    Integer idProducto;
}
