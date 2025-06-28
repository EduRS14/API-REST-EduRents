package com.ingsoft.tf.api_edurents.service.Interface.auth.seller;

import java.util.List;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;

public interface SellerAuthExchangeOfferService {

    List<ShowExchangeOfferDTO> obtenerIntercambiosPorProductoPorVendedor(Integer idProduct, Integer idSeller);

    void aceptarOferta(Integer id);
    void rechazarOferta(Integer id);


}
