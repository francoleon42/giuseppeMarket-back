package com.giuseppemarket.service.impl;

import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.model.Caja;
import com.giuseppemarket.model.Producto;
import com.giuseppemarket.repository.ICajaRepository;
import com.giuseppemarket.service.ICajaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CajaServiceImpl implements ICajaService {
    private final ICajaRepository cajaRepository;

    @Override
    public void incrementarCaja(Integer idUsuario,double montoFinalAgregado) {
        Caja caja = obtenerCajaActualByUser(idUsuario);
        caja.setMontoFinal(caja.getMontoFinal() + montoFinalAgregado);
        cajaRepository.save(caja);
    }

    private Caja obtenerCajaActualByUser(Integer idUser) {
        return cajaRepository.findByUsuarioIdAndCierreIsNull(idUser)
                .orElseThrow(() -> new NotFoundException("No se encontro la caja actual del usuario : " + idUser));
    }
}
