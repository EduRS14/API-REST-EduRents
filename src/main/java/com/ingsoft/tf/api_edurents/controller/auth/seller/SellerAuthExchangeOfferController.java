package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthExchangeOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Ofertas de Intercambio_Vendedor", description = "API de Gestion de ofertas de intercambio para un vendedor registrado")
@RequiredArgsConstructor
@RestController
@RequestMapping("/seller/auth/exchanges")
public class SellerAuthExchangeOfferController {

    private final SellerAuthExchangeOfferService sellerAuthExchangeOfferService;

    // HU 02

    @Operation(
            summary = "Obtener un intercambio por su ID y el ID del vendedor",
            description = "Permite a un vendedor obtener los detalles de un intercambio para un producto específico " +
                    "por su ID y el ID del vendedor. Se devuelve un objeto ShowExchangeOfferDTO con los detalles del intercambio.",
            tags = {"intercambios", "vendedor", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowExchangeOfferDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @GetMapping("/product/{idProduct}/seller/{idSeller}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorProductoPorVendedor(
            @PathVariable Integer idProduct,
            @PathVariable Integer idSeller) {

        List<ShowExchangeOfferDTO> intercambios = sellerAuthExchangeOfferService
                .obtenerIntercambiosPorProductoPorVendedor(idProduct, idSeller);

        return ResponseEntity.ok(intercambios);
    }


}
