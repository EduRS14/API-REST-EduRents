package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
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
@RestController
@Tag(name = "Producto_Publico", description = "API de Gestion de Productos para un usuario registrado/no registrado")
@RequestMapping("/public/products")
public class PublicProductController {

    private final PublicProductService publicProductService;

    //HU 01
    @GetMapping("/{id}")
    public ResponseEntity<ShowProductDTO> obtenerProductoPorId(@PathVariable Integer id){
        ShowProductDTO producto = publicProductService.obtenerProductoPorId(id);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    // HU 03
    @GetMapping
    public ResponseEntity<List<ShowProductDTO>> obtenerProductos(){
        List<ShowProductDTO> productos = publicProductService.obtenerTodosLosProductos();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    //HU 04
    @Operation(
            summary = "Obtener productos por ID de carrera",
            description = "Devuelve una lista de productos asociados a una carrera especifica, identificada por su ID.",
            tags = {"Productos", "Carreras"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Carrera no encontrada o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarrera(@PathVariable Integer idCareer) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarrera(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por ID de curso",
            description = "Devuelve una lista de productos asociados a un curso especifico, identificado por su ID.",
            tags = {"Productos", "Cursos"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Curso no encontrado o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/course/{idCourse}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCurso(@PathVariable Integer idCourse) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCurso(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por curso y carrera",
            description = "Filtra los productos segun el curso y la carrera, ambos identificados por su ID.",
            tags = {"Productos", "Cursos", "Carreras"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Curso o carrera no encontrada, o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{carreraId}/course/{cursoId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCursoYCarrera(@PathVariable Integer carreraId, @PathVariable Integer cursoId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCursoYCarrera(carreraId, cursoId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por ID de categoria",
            description = "Devuelve una lista de productos que pertenecen a una categoria especifica, identificada por su ID.",
            tags = {"Productos", "Categorias"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria no encontrada o sin productos asociados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCategoria(categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por carrera, curso y categoria",
            description = "Filtra los productos combinando los filtros de carrera, curso y categoria. Todos deben ser identificados por su ID.",
            tags = {"Productos", "Carreras", "Cursos", "Categorias"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos encontrada",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron productos con los filtros especificados"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("career/{idCareer}/course/{idCourse}/category/{idCategory}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarreraCursoCategoria(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarreraCursoYCategoria(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05
    @Operation(
            summary = "Obtener productos por carrera ordenados por vistas",
            description = "Filtra los productos por ID de carrera y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"Productos", "Carreras", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerOrderByView(@PathVariable Integer idCareer){
        publicProductService.obtenerProductosPorCarreraOrdenarPorVistas(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por curso ordenados por vistas",
            description = "Filtra los productos por ID de curso y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"Productos", "Cursos", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/course/{idCourse}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCourseOrderByView(@PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCursoOrdenarPorVistas(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por carrera y curso ordenados por vistas",
            description = "Filtra los productos por carrera y curso, y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"Productos", "Carreras", "Cursos", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/course/{idCourse}/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCarreraPorCursoOrdenarPorVistas(idCareer, idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }
    @Operation(
            summary = "Obtener productos por categoria ordenados por vistas",
            description = "Filtra los productos por ID de categoria y los ordena segun la cantidad de vistas de mayor a menor.",
            tags = {"Productos", "Categorias", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCategoryOrderByView(@PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCategoriaOrdernarPorVistas(idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }
    @Operation(
            summary = "Obtener productos por carrera, curso y categoria ordenados por vistas",
            description = "Filtra los productos combinando carrera, curso y categoria, y los ordena por vistas de mayor a menor.",
            tags = {"Productos", "Carreras", "Cursos", "Categorias", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos para mostrar"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/career/{idCareer}/course/{idCourse}/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseCategoryOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    // HU 06
    @Operation(
            summary = "Obtener productos trendy (mas vistos)",
            description = "Retorna un top de productos basado en la cantidad de vistas. El top es un 10% o 5% del total de productos, segun la cantidad disponible.",
            tags = {"Productos", "Tendencias", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos trendy ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos disponibles"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/products/trendy")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTrendy(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorVistas();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos con mas propuestas de intercambio",
            description = "Retorna un top de productos basado en la cantidad de propuestas de intercambio recibidas. Se calcula segun la cantidad de registros en la tabla exchangeOffer. El top es un 10% o 5% del total de productos.",
            tags = {"Productos", "Intercambios", "Propuestas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por cantidad de intercambios",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos con propuestas de intercambio"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/products/top-exchanges")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTopExchange(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorCantidadDeIntercambios();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos recientes",
            description = "Retorna productos ordenados por fecha de creacion descendente. Puede considerarse solo un orden o un top limitado segun definicion futura.",
            tags = {"Productos", "Recientes", "Fechas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos ordenada por fecha de creacion",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos recientes disponibles"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/products/recents")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosRecents(){
        List<ShowProductDTO> productos = publicProductService.obtener10ProductosRecientes();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }






}
