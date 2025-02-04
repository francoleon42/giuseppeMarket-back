package com.giuseppemarket.service.impl;

import com.giuseppemarket.dto.caja.*;
import com.giuseppemarket.dto.login.GetUserDTO;
import com.giuseppemarket.model.Caja;
import com.giuseppemarket.model.MontoReal;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.repository.ICajaRepository;
import com.giuseppemarket.repository.IMontoRealRepository;
import com.giuseppemarket.service.ICajaService;
import com.giuseppemarket.utils.enums.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CajaServiceImpl implements ICajaService {
    private final ICajaRepository cajaRepository;
    private final IMontoRealRepository montoRealRepository;

    @Override
    public void incrementarCaja(Integer idUsuario, double montoFinalAgregado) {
        Caja caja = obtenerCajaActualByUser(idUsuario);
        if (caja != null) {
            caja.setMontoFinal(caja.getMontoFinal() + montoFinalAgregado);
            cajaRepository.save(caja);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Para realizar una venta primero debe abrir una caja");
        }

    }

    @Override
    public CajaAperturaResponseDTO aperturaCaja(Usuario usuario, CajaAperturaRequestDTO cajaAperturaRequestDTO) {
        Caja caja = obtenerCajaActualByUser(usuario.getId());
        if (caja == null) {
            Caja newCaja = Caja.builder()
                    .apertura(Instant.now())
                    .montoInicial(cajaAperturaRequestDTO.getMontoInicial())
                    .usuario(usuario)
                    .montoReal(MontoReal.builder().build())
                    .build();
            cajaRepository.save(newCaja);
            return CajaAperturaResponseDTO.builder()
                    .id(newCaja.getId())
                    .apertura(newCaja.getApertura().atOffset(ZoneOffset.UTC)
                            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                    .montoInicial(newCaja.getMontoInicial())
                    .usuario(GetUserDTO.builder()
                            .id(usuario.getId())
                            .username(usuario.getUsername())
                            .role(usuario.getRol())
                            .estado(usuario.getEstadoUsuario().toString())
                            .build())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "En este momento tiene una caja en apertura: ");
        }
    }

    @Override
    public CajaCerrarResponseDTO cerrarCaja(Usuario usuario, CajaCerrarRequestDTO cajaCerrarRequestDTO) {
        Caja caja = obtenerCajaActualByUser(usuario.getId());

        if (caja != null) {
            MontoReal montoReal = caja.getMontoReal();
            double montoTarjetas = cajaCerrarRequestDTO.getMontoRealDTO().getMontoTarjetas();
            double montoTransferencias = cajaCerrarRequestDTO.getMontoRealDTO().getMontoTransferencias();
            double montoEfectivo = cajaCerrarRequestDTO.getMontoRealDTO().getMontoEfectivo();
            double montoOtros = cajaCerrarRequestDTO.getMontoRealDTO().getMontoOtros();
            double montoTotal = montoTarjetas + montoTransferencias + montoEfectivo + montoOtros;
            montoReal.setMontoTarjetas(montoTarjetas);
            montoReal.setMontoTransferencias(montoTransferencias);
            montoReal.setMontoEfectivo(montoEfectivo);
            montoReal.setMontoOtros(montoOtros);
            montoReal.setMontoTotal(montoTotal);
            montoReal.setObservaciones(cajaCerrarRequestDTO.getMontoRealDTO().getObservaciones());
            montoRealRepository.save(montoReal);

            caja.setCierre(Instant.now());
            caja.setObservaciones(cajaCerrarRequestDTO.getObservacionesCaja());
            cajaRepository.save(caja);

            return CajaCerrarResponseDTO.builder()
                    .montoFinal(caja.getMontoFinal())
                    .montoReal(montoTotal)
                    .resultado(caja.getMontoFinal() - montoTotal)
                    .estado(caja.getMontoFinal() - montoTotal == 0.00 ? "CONSISTENTE" : "INCONSISTENTE")
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "En este momento no tines ninguna caja abierta ");
        }
    }

    @Override
    public Caja obtenerCajaActualByUser(Integer idUser) {
        return cajaRepository.findByUsuarioIdAndCierreIsNull(idUser)
                .orElse(null);
    }


    @Override
    public List<CajaHistorialResponseDTO> obtenerCajasPorFechas(CajaPorFechaRequestDTO cajaPorFechaRequestDTO) {
        List<Caja> cajas = cajaRepository.findCajasByFechaAperturaBetween(cajaPorFechaRequestDTO.getDesde(), cajaPorFechaRequestDTO.getHasta());
        return cajas.stream().map(this::convertirACajaHistorialResponseDTO).collect(Collectors.toList());
    }

    private CajaHistorialResponseDTO convertirACajaHistorialResponseDTO(Caja caja) {
        CajaResponseDTO cajaResponseDTO = CajaResponseDTO.builder()
                .id(caja.getId())
                .apertura(caja.getApertura())
                .cierre(caja.getCierre())
                .montoInicial(caja.getMontoInicial())
                .montoFinal(caja.getMontoFinal())
                .observaciones(caja.getObservaciones())
                .usuario(GetUserDTO.builder()
                        .id(caja.getUsuario().getId())
                        .username(caja.getUsuario().getUsername())
                        .role(caja.getUsuario().getRol())
                        .estado(caja.getUsuario().getEstadoUsuario().toString())
                        .build())
                .build();

        MontoReal montoReal = caja.getMontoReal();
        MontoRealResponseDTO montoRealResponseDTO = montoReal != null ? MontoRealResponseDTO.builder()
                .id(montoReal.getId())
                .montoTotal(montoReal.getMontoTotal())
                .montoTarjetas(montoReal.getMontoTarjetas())
                .montoTransferencias(montoReal.getMontoTransferencias())
                .montoEfectivo(montoReal.getMontoEfectivo())
                .montoOtros(montoReal.getMontoOtros())
                .observaciones(montoReal.getObservaciones())
                .build() : null;

        return CajaHistorialResponseDTO.builder()
                .cajaResponseDTO(cajaResponseDTO)
                .montoRealResponseDTO(montoRealResponseDTO)
                .build();
    }
}


