package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seller/auth/exchanges")
public class SellerAuthExchangeOfferController {

    private final SellerAuthExchangeOfferService sellerAuthExchangeOfferService;

    // HU 02

    @GetMapping("/product/{idProduct}/seller/{idSeller}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorProductoPorVendedor(
            @PathVariable Integer idProduct,
            @PathVariable Integer idSeller) {

        List<ShowExchangeOfferDTO> intercambios = sellerAuthExchangeOfferService
                .obtenerIntercambiosPorProductoPorVendedor(idProduct, idSeller);

        return ResponseEntity.ok(intercambios);
    }


}
