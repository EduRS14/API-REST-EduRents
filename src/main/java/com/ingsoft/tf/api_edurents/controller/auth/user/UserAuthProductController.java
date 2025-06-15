package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthProductService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "Producto_Publico", description = "API de Gestion de Productos para un usuario registrado")
@RequestMapping("/user/auth/products")
public class UserAuthProductController {
    private final UserAuthProductService userAuthProductService;

    // HU03

    @GetMapping("/{sellerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedor(@PathVariable Integer sellerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedor(sellerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/course/{courseId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCurso(
            @PathVariable Integer sellerId, @PathVariable Integer courseId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCurso(sellerId, courseId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/career/{careerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCarrera(
            @PathVariable Integer sellerId, @PathVariable Integer careerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCarrera(sellerId, careerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCategoria(
            @PathVariable Integer sellerId, @PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCategoria(sellerId, categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05
    @Operation(
            summary = "Sumar una vista al producto",
            description = "Cada vez que un usuario visualiza un producto, se incrementa en 1 el contador de vistas.",
            tags = {"Productos", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Vista registrada correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @PutMapping("/{idProduct}/views")
    public ResponseEntity<ShowProductDTO> sumarViews(@PathVariable Integer idProduct){
        userAuthProductService.sumarView(idProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos por vendedor ordenados por vistas",
            description = "Obtiene todos los productos publicados por un vendedor especifico y los ordena segun el numero de vistas.",
            tags = {"Productos", "Vistas", "Vendedores"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos del vendedor ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "El vendedor no tiene productos registrados"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vendedor no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping("/seller/{idSeller}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductSellerOrderByView(@PathVariable Integer idSeller){
        userAuthProductService.obtenerProductosPorIdVendedorOrdenarPorVistas(idSeller);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Obtener productos recomendados por carrera",
            description = "Muestra una lista de productos recomendados para la carrera indicada, ordenados por vistas de mayor a menor.",
            tags = {"Productos", "Recomendaciones", "Carreras", "Vistas"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de productos recomendados ordenada por vistas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ShowProductDTO.class)), mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No hay productos recomendados para esta carrera"
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
    @GetMapping("/recomendados/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> recomendarProductos(@PathVariable Integer idCareer){
        userAuthProductService.obtenerProductosRecomendados(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

}
