package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthExchangeOfferService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "Aceptar o rechazar un intercambio",
            description = "Permite a un vendedor aceptar o rechazar un intercambio. " +
                    "El vendedor debe proporcionar el ID del intercambio, su ID y un booleano que indica si acepta o rechaza el intercambio.",
            tags = {"intercambios", "vendedor", "auth_vendedor", "patch"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema()) }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema()) }
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema()) }
            )
    })
    @PatchMapping("/{idExchangeOffer}/seller/{idSeller}")
    public ResponseEntity<Void> aceptarIntercambio(
            @PathVariable Integer idExchangeOffer,
            @PathVariable Integer idSeller,
            @RequestParam Boolean aceptar) {

        sellerAuthExchangeOfferService.aceptarIntercambio(idExchangeOffer, idSeller, aceptar);
        return ResponseEntity.ok().build();
    }


}
