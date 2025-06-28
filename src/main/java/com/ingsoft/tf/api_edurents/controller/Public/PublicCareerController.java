package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.university.CareerDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCareerService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Carrera_publica", description = "API de Gestion de Carreras")
@RestController
@RequestMapping("/public/careers")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicCareerController {

    private final PublicCareerService publicCareerService;

    @Operation(
            summary = "Obtener todas las carreras",
            description = "Devuelve una lista con todas las carreras registradas.",
            tags = {"carreras", "varios", "todos", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de carreras obtenida exitosamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CareerDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping
    public ResponseEntity<List<CareerDTO>> getAllCareers(){
        List<CareerDTO> careerDtoList = publicCareerService.getAllCareers();
        return new ResponseEntity<List<CareerDTO>>(careerDtoList, HttpStatus.OK);
    }

}
