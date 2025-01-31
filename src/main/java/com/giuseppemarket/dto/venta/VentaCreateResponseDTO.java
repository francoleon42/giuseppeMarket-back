package com.giuseppemarket.dto.venta;

import com.giuseppemarket.model.Item;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.utils.enums.CondicionVenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VentaCreateResponseDTO {
    Integer comprobante;
    Instant fechaHora;
    String observaciones;
    double subtotal;
    double descuento;
    double total;
    String condicionVenta;
//    List<Item> items;
}
