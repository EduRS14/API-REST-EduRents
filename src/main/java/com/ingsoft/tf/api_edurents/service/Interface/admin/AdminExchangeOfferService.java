package com.ingsoft.tf.api_edurents.service.Interface.admin;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;

import java.util.List;

public interface AdminExchangeOfferService {
    List<ShowExchangeOfferDTO> obtenerTodosLosIntercambios();


    ShowExchangeOfferDTO obtenerIntercambioPorId(Integer id);


}
