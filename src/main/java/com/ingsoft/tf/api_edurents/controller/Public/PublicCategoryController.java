package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCategoryService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Categoria_publica", description = "API de Gestion de Categorias")
@RestController
@RequestMapping("/public/categories")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    @Operation(
            summary = "Obtener todas las categorias",
            description = "Devuelve una lista con todas las categorias disponibles.",
            tags = {"categorias", "varios", "todos", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de categorias obtenida exitosamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categoryDTOList = publicCategoryService.getAllCategories();
        return new ResponseEntity<List<CategoryDTO>>(categoryDTOList, HttpStatus.OK);
    }
}
