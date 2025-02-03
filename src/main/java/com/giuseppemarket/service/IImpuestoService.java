package com.giuseppemarket.service;

import com.giuseppemarket.dto.impuesto.*;

import java.util.List;

public interface IImpuestoService {

    ImpuestoResponseDTO crear(ImpuestoRequestDTO impuestoRequestDTO);
    ImpuestoResponseDTO update(ImpuestoRequestDTO impuestoRequestDTO, Integer id);
    String remove(Integer id);

    ImpuestoAsignacionResponseDTO asignar(ImpuestoAsignacionRequestDTO impuestoAsignacionRequestDTO);
    String desasignar(ImpuestoDesasignacionRequestDTO ipuestoDesasignacionRequestDTO) ;

    List<ImpuestoResponseDTO> obtenerAllAsignacionesDeProducto(Integer idProducto);
}
