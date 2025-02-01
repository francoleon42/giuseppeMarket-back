package com.giuseppemarket.service;

import com.giuseppemarket.dto.impuesto.ImpuestoRequestDTO;
import com.giuseppemarket.dto.impuesto.ImpuestoResponseDTO;

public interface IImpuestoService {

    ImpuestoResponseDTO crear(ImpuestoRequestDTO impuestoRequestDTO);
    ImpuestoResponseDTO update(ImpuestoRequestDTO impuestoRequestDTO, Integer id);
    String remove(Integer id);
}
