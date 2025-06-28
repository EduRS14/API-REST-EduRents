package com.ingsoft.tf.api_edurents.controller.auth.seller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthExchangeOfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Tag(name = "Ofertas de Intercambio_Vendedor", description = "API de Gestion de ofertas de intercambio para un vendedor registrado")
@RestController
@RequestMapping("/seller/auth/exchanges")
@PreAuthorize("hasAnyRole('SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class SellerAuthExchangeOfferController {

    private final SellerAuthExchangeOfferService sellerAuthExchangeOfferService;

    // HU 02
    @Operation(
            summary = "Obtener intercambios por ID del producto y el ID del vendedor",
            description = "Permite a un vendedor obtener los detalles de un intercambio para un producto específico " +
                    "por su ID y el ID del vendedor. Se devuelve un objeto ShowExchangeOfferDTO con los detalles del intercambio.",
            tags = {"intercambios", "producto", "vendedor", "varios", "auth_vendedor", "get"}
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

    // ACEPTAR O RECHAZAR INTERCAMBIOS

    @Operation(
        summary = "Aceptar una oferta de intercambio",
        description = "Permite al vendedor aceptar una oferta de intercambio específica por su ID.",
        tags = {"intercambios", "vendedor", "auth_vendedor", "put"}
    )
    @PutMapping("/{id}/accept")
        public ResponseEntity<Void> aceptarOferta(@PathVariable Integer id) {
        sellerAuthExchangeOfferService.aceptarOferta(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Rechazar una oferta de intercambio",
        description = "Permite al vendedor rechazar una oferta de intercambio específica por su ID.",
        tags = {"intercambios", "vendedor", "auth_vendedor", "put"}
    )
    @PutMapping("/{id}/reject")
        public ResponseEntity<Void> rechazarOferta(@PathVariable Integer id) {
        sellerAuthExchangeOfferService.rechazarOferta(id);
        return ResponseEntity.ok().build();
    }



}
