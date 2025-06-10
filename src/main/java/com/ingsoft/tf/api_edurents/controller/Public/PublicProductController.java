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

@Tag(name = "Producto_Publico", description = "API de Gestion de Productos para un usuario registrado/no registrado")
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/products")
public class PublicProductController {

    private final PublicProductService publicProductService;

    //HU 01
    @Operation(
            summary = "Obtener un producto por su ID",
            description = "Permite a un usuario obtener los detalles de un producto específico por su ID. " +
                    "Se devuelve un objeto ShowProductDTO con los detalles del producto, como su nombre, descripción, " +
                    "precio y estado de disponibilidad.",
            tags = {"productos", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
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
    @GetMapping("/{id}")
    public ResponseEntity<ShowProductDTO> obtenerProductoPorId(@PathVariable Integer id){
        ShowProductDTO producto = publicProductService.obtenerProductoPorId(id);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    // HU 03
    @Operation(
            summary = "Obtener todos los productos",
            description = "Permite a un usuario obtener una lista de todos los productos disponibles. " +
                    "Se devuelve una lista de objetos ShowProductDTO, cada uno representando un producto con sus detalles.",
            tags = {"productos", "publico", "get"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class))
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
    @GetMapping
    public ResponseEntity<List<ShowProductDTO>> obtenerProductos(){
        List<ShowProductDTO> productos = publicProductService.obtenerTodosLosProductos();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    //HU 04

    @GetMapping("/career/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarrera(@PathVariable Integer idCareer) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarrera(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/course/{idCourse}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCurso(@PathVariable Integer idCourse) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCurso(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/career/{carreraId}/course/{cursoId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCursoYCarrera(@PathVariable Integer carreraId, @PathVariable Integer cursoId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCursoYCarrera(carreraId, cursoId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCategoria(@PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCategoria(categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("career/{idCareer}/course/{idCourse}/category/{idCategory}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorCarreraCursoCategoria(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory) {
        List<ShowProductDTO> productos = publicProductService.obtenerProductosPorCarreraCursoYCategoria(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05

    @GetMapping("/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerOrderByView(@PathVariable Integer idCareer){
        publicProductService.obtenerProductosPorCarreraOrdenarPorVistas(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/course/{idCourse}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCourseOrderByView(@PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCursoOrdenarPorVistas(idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/course/{idCourse}/career/{idCareer}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse){
        publicProductService.obtenerProductosPorCarreraPorCursoOrdenarPorVistas(idCareer, idCourse);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCategoryOrderByView(@PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCategoriaOrdernarPorVistas(idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/career/{idCareer}/course/{idCourse}/category/{idCategory}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductCareerCourseCategoryOrderByView(@PathVariable Integer idCareer, @PathVariable Integer idCourse, @PathVariable Integer idCategory){
        publicProductService.obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(idCareer, idCourse, idCategory);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    // HU 06

    @GetMapping("/products/trendy")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTrendy(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorVistas();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/products/top-exchanges")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosTopExchange(){
        List<ShowProductDTO> productos = publicProductService.obtenerTop10ProductosPorCantidadDeIntercambios();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/products/recents")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosRecents(){
        List<ShowProductDTO> productos = publicProductService.obtener10ProductosRecientes();
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }






}
