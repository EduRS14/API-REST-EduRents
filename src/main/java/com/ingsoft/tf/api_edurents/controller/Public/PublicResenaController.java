package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Resena_Publico", description = "API de gestion de reseñas públicas")
@RestController
@RequestMapping("/public/reviews")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicResenaController {

    private final PublicResenaService publicResenaService;

    @Operation(
            summary = "Buscar reseñas por vendedor",
            description = "Obtiene una lista de reseñas asociadas a un vendedor específico",
            tags = {"reseñas", "público", "vendedor"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResenaResponseDTO.class))
                    )
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
    @RequestMapping("/seller/{idVendedor}")
    public ResponseEntity<List<ResenaResponseDTO>> obtenerResenasPorVendedor(@PathVariable Integer idVendedor) {
        List<ResenaResponseDTO> resenas = publicResenaService.obtenerResenasPorVendedor(idVendedor);
        return new ResponseEntity<List<ResenaResponseDTO>>(resenas, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener una reseña por su ID",
            description = "Obtiene una reseña específica por su ID",
            tags = {"reseñas", "público", "ID"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResenaResponseDTO.class)
                    )
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
    @RequestMapping("/{idResena}")
    public ResponseEntity<ResenaResponseDTO> obtenerResenaPorId(@PathVariable Integer idResena) {
        ResenaResponseDTO resena = publicResenaService.obtenerResena(idResena);
        return new ResponseEntity<ResenaResponseDTO>(resena, HttpStatus.OK);
    }

}
