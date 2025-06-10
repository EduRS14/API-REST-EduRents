package com.ingsoft.tf.api_edurents.service.Interface.auth.seller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;

import java.util.List;

public interface SellerAuthExchangeOfferService {

    List<ShowExchangeOfferDTO> obtenerIntercambiosPorProductoPorVendedor(Integer idProduct, Integer idSeller);

}
