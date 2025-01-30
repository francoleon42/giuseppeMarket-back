package com.giuseppemarket.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ApiError {
    private String error;
    private String message;
    private Integer status;
}
