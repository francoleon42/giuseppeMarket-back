package com.giuseppemarket.dto.login;

import com.giuseppemarket.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateResponseDTO {
    private String username;
    private Rol role;
    private RolEntityDTO roleEntity;
}
