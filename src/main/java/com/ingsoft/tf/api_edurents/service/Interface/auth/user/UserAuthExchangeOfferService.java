package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;

import java.util.List;

public interface UserAuthExchangeOfferService {

    // HU02
    ShowExchangeOfferDTO crearIntercambio(ExchangeOfferDTO intercambioDTO);
    ShowExchangeOfferDTO obtenerIntercambioPorIdPorUsuarioId(Integer id, Integer idUser);
    List<ShowExchangeOfferDTO> obtenerIntercambiosPorUsuario(Integer id);
    List<ShowExchangeOfferDTO> obtenerIntercambiosPorVendedor(Integer id);
    ShowExchangeOfferDTO actualizarIntercambio(Integer id, ExchangeOfferDTO intercambioDTO);
    void eliminarIntercambio(Integer id);
}
