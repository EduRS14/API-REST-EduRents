package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.CourseCareerDTO;
import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCourseService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Cursos", description = "API de filtros para los cursos de una carrera")
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/courses")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicCourseController {

    private final PublicCourseService publicCourseService;

    @Operation(
            summary = "Obtener cursos por ID de carrera",
            description = "Devuelve una lista de cursos asociados a una carrera específica, identificada por su ID.",
            tags = {"cursos", "carrera", "varios", "publico", "get"}
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

    @GetMapping("/career")
    public ResponseEntity<List<CourseCareerDTO>> obtenerCursosConCarrera() {
        List<CourseCareerDTO> courseCareerList = publicCourseService.obtenerTodosLosCursosConCarreras();
        return new ResponseEntity<List<CourseCareerDTO>>(courseCareerList, HttpStatus.OK);
    }


}
