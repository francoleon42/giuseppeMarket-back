package com.nameproyect.dto.login;

import com.nameproyect.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String username;
    private String token;
    private Rol role;
    private RolEntityDTO roleEntity;
}

