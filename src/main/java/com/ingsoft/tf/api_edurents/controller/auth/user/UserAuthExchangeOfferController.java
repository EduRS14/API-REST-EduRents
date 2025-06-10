package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthExchangeOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ofertas de Intercambio_Usuario", description = "API de Gestion de ofertas de intercambio para un usuario registrado")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/exchanges")
public class UserAuthExchangeOfferController {

    private final UserAuthExchangeOfferService userAuthExchangeOfferService;

    // HU02

    @Operation(
            summary = "Crear un intercambio",
            description = "Permite a un usuario registrado crear una oferta de intercambio para un producto. " +
                    "Se espera un objeto ExchangeOfferDTO con los detalles del intercambio, " +
                    "y se devuelve un objeto ShowExchangeOfferDTO con la oferta de intercambio creada.",
            tags = {"intercambios", "usuario", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    content = { @Content(schema = @Schema(implementation = ShowExchangeOfferDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowExchangeOfferDTO> crearIntercambio(@Valid @RequestBody ExchangeOfferDTO intercambioDTO){
        ShowExchangeOfferDTO ofertaIntercambio = userAuthExchangeOfferService.crearIntercambio(intercambioDTO);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener un intercambio por su ID y el ID del usuario",
            description = "Permite a un usuario obtener los detalles de un intercambio específico por el ID de la oferta y su ID de usuario. " +
                    "Se devuelve un objeto ShowExchangeOfferDTO con los detalles del intercambio.",
            tags = {"intercambios", "usuario", "id", "get"}
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
    @GetMapping("/{id}/user/{idUser}")
    public ResponseEntity<ShowExchangeOfferDTO> obtenerIntercambioPorIdPorUsuarioId(@PathVariable Integer id, @PathVariable Integer idUser){
        ShowExchangeOfferDTO ofertaIntercambio = userAuthExchangeOfferService.obtenerIntercambioPorIdPorUsuarioId(id, idUser);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todos los intercambios hechos por un usuario",
            description = "Permite a un usuario obtener una lista de todas sus ofertas de intercambio hechas. " +
                    "Se devuelve una lista de objetos ShowExchangeOfferDTO con los detalles de cada intercambio.",
            tags = {"intercambios", "usuario", "todos", "get"}
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
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorUsuario(@PathVariable Integer id){
        List<ShowExchangeOfferDTO> ofertasIntercambio = userAuthExchangeOfferService.obtenerIntercambiosPorUsuario(id);
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todos los intercambios hechos por un vendedor",
            description = "Permite a un usuario obtener una lista de todas las ofertas de intercambio hechas hacia un vendedor. " +
                    "Se devuelve una lista de objetos ShowExchangeOfferDTO con los detalles de cada intercambio.",
            tags = {"intercambios", "usuario", "vendedor", "todos", "get"}
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
    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorVendedor(@PathVariable Integer id){
        List<ShowExchangeOfferDTO> ofertasIntercambio = userAuthExchangeOfferService.obtenerIntercambiosPorVendedor(id);
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar un intercambio por su ID",
            description = "Permite a un usuario registrado actualizar los detalles de un intercambio específico por su ID. " +
                    "Se espera un objeto ExchangeOfferDTO con los nuevos detalles del intercambio, " +
                    "y se devuelve un objeto ShowExchangeOfferDTO con la oferta de intercambio actualizada.",
            tags = {"intercambios", "usuario", "put"}
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
    @PutMapping("/{id}")
    public ResponseEntity<ShowExchangeOfferDTO> actualizarIntercambio(@PathVariable Integer id, @Valid @RequestBody ExchangeOfferDTO intercambioDTO){
        ShowExchangeOfferDTO ofertaIntercambio = userAuthExchangeOfferService.actualizarIntercambio(id, intercambioDTO);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar un intercambio por su ID",
            description = "Permite a un usuario registrado eliminar un intercambio específico por su ID. " +
                    "No devuelve contenido, pero indica que la operación se realizó con éxito.",
            tags = {"intercambios", "usuario", "delete"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    content = { @Content(schema = @Schema()) }
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarIntercambio(@PathVariable Integer id) {
        userAuthExchangeOfferService.eliminarIntercambio(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }

}
