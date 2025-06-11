package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCourseService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Tag(name="Cursos", description = "API de filtros para los cursos de una carrera")
@RestController
@RequestMapping("/public/courses")
public class PublicCourseController {

    private final PublicCourseService publicCourseService;

    @Operation(
            summary = "Obtener cursos por ID de carrera",
            description = "Devuelve una lista de cursos asociados a una carrera específica, identificada por su ID.",
            tags = {"Cursos", "Carreras"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cursos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrera no encontrada"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{id}")
    public ResponseEntity<List<CourseDTO>> obtenerCursosPorCarrera(@PathVariable("id") Integer id){
        List<CourseDTO> cursos = publicCourseService.obtenerCursosPorCarrera(id);
        return new ResponseEntity<List<CourseDTO>>(cursos, HttpStatus.OK);
    }
}
