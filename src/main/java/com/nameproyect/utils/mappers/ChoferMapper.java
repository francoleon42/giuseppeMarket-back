package com.nameproyect.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nameproyect.dto.chofer.ChoferResponseDTO;
import com.nameproyect.model.Chofer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChoferMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public ChoferMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public ChoferResponseDTO obtenerChoferDTO(Chofer chofer) {
        if (chofer == null) {
            return null;
        }

        ChoferResponseDTO choferResponseDTO = new ChoferResponseDTO();
        choferResponseDTO.setIdChofer(chofer.getId());
        choferResponseDTO.setNombre(chofer.getNombre());

        return choferResponseDTO;
    }


    public List<ChoferResponseDTO> obtenerListaChoferDTO(List<Chofer> choferes) {
        if (choferes == null) {
            return Collections.emptyList();
        }
        return choferes.stream()
                .map(this::obtenerChoferDTO)
                .collect(Collectors.toList());
    }
}
