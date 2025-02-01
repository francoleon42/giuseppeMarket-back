package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.impuesto.ImpuestoRequestDTO;
import com.giuseppemarket.dto.impuesto.ImpuestoResponseDTO;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Impuesto;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.repository.IImpuestoRepository;
import com.giuseppemarket.service.IImpuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImpuestoServiceImpl implements IImpuestoService {
    private final IImpuestoRepository impuestoRepository;

    @Override
    public ImpuestoResponseDTO crear(ImpuestoRequestDTO impuestoRequestDTO) {
        Impuesto impuesto = Impuesto.builder()
                .valor(impuestoRequestDTO.getValor())
                .nombre(impuestoRequestDTO.getNombre())
                .build();
        impuestoRepository.save(impuesto);
        return ImpuestoResponseDTO.builder()
                .valor(impuestoRequestDTO.getValor())
                .nombre(impuestoRequestDTO.getNombre())
                .build();

    }

    @Override
    public ImpuestoResponseDTO update(ImpuestoRequestDTO impuestoRequestDTO, Integer id) {
        Impuesto impuesto = impuestoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el impuesto con el id: " + id));

        impuesto.setValor(impuestoRequestDTO.getValor());
        impuesto.setNombre(impuestoRequestDTO.getNombre());
        impuestoRepository.save(impuesto);
        return ImpuestoResponseDTO.builder()
                .valor(impuestoRequestDTO.getValor())
                .nombre(impuestoRequestDTO.getNombre())
                .build();
    }

    @Override
    public String remove(Integer id) {
        Impuesto impuesto = impuestoRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el impuesto con el id: " + id));

        impuestoRepository.delete(impuesto);
        return "REMOVIDO EL IMPUESTO ID: " + id;
    }


}
