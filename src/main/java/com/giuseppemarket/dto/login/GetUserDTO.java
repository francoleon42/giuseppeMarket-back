package com.giuseppemarket.dto.login;

import com.giuseppemarket.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserDTO {
    private Integer id;
    private String username;
    private Rol role;
    private String estado;
}
