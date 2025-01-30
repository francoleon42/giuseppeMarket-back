package com.giuseppemarket.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@Getter
public abstract class RolEntityDTO {
    private Integer id;
}
