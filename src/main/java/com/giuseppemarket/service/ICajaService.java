package com.giuseppemarket.service;

import com.giuseppemarket.dto.caja.*;
import com.giuseppemarket.model.Caja;
import com.giuseppemarket.model.Usuario;

import java.time.Instant;
import java.util.List;

public interface ICajaService {

    void incrementarCaja(Integer idUsuario,double montoFinalAgregado);

    CajaAperturaResponseDTO aperturaCaja (Usuario usuario , CajaAperturaRequestDTO cajaAperturaRequestDTO);
    CajaCerrarResponseDTO cerrarCaja(Usuario usuario, CajaCerrarRequestDTO cajaCerrarRequestDTO);
    Caja obtenerCajaActualByUser(Integer idUser);

    List<CajaHistorialResponseDTO> obtenerCajasPorFechas(CajaPorFechaRequestDTO cajaPorFechaRequestDTO);
}
