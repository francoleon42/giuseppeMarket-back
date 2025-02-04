package com.giuseppemarket.service;

import com.giuseppemarket.dto.caja.CajaAperturaRequestDTO;
import com.giuseppemarket.dto.caja.CajaAperturaResponseDTO;
import com.giuseppemarket.dto.caja.CajaCerrarRequestDTO;
import com.giuseppemarket.dto.caja.CajaCerrarResponseDTO;
import com.giuseppemarket.model.Usuario;

public interface ICajaService {

    void incrementarCaja(Integer idUsuario,double montoFinalAgregado);

    CajaAperturaResponseDTO aperturaCaja (Usuario usuario , CajaAperturaRequestDTO cajaAperturaRequestDTO);
    CajaCerrarResponseDTO cerrarCaja(Usuario usuario, CajaCerrarRequestDTO cajaCerrarRequestDTO);
}
