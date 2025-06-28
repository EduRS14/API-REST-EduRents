package com.ingsoft.tf.api_edurents.controller.admin;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminExchangeOfferService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Oferta de intercambio_Administracion", description = "API de Gestion de Transacciones desde la administracion")
@RestController
@RequestMapping("admin/exchanges")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class AdminExchangeOfferController {

    private final AdminExchangeOfferService adminExchangeOfferService;

    @Operation(
            summary = "Obtener todos los intercambios",
            description = "Permite a la administración obtener una lista de todas las ofertas de intercambio" +
                    "Se devuelve una lista de objetos ShowExchangeOfferDTO con los detalles de cada intercambio.",
            tags = {"intercambios", "todos", "admin", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShowExchangeOfferDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())}
            )
    })
    @GetMapping
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambios(){
        List<ShowExchangeOfferDTO> ofertasIntercambio = adminExchangeOfferService.obtenerTodosLosIntercambios();
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener un intercambio por su ID",
            description = "Permite a un usuario obtener los detalles de un intercambio específico por el ID de la oferta. " +
                    "Se devuelve un objeto ShowExchangeOfferDTO con los detalles del intercambio.",
            tags = {"intercambios", "id", "admin", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ShowExchangeOfferDTO.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = {@Content(schema = @Schema())}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShowExchangeOfferDTO> obtenerIntercambioPorId(@PathVariable Integer id){
        ShowExchangeOfferDTO ofertaIntercambio = adminExchangeOfferService.obtenerIntercambioPorId(id);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }





}
