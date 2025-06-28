package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthProductService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Producto_Usuario", description = "API de gestion de productos para un usuario registrado")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/products")
@PreAuthorize("hasAnyRole('USER', 'SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class UserAuthProductController {
    private final UserAuthProductService userAuthProductService;

    // HU03

    @Operation(
            summary = "Obtener productos por la ID de un vendedor",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos del vendedor.",
            tags = {"productos", "vendedor", "varios", "todos", "auth_usuario", "get"}
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
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedor(@PathVariable Integer sellerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedor(sellerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por la ID de un vendedor y curso",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID y el ID de un curso." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos de un vendedor para ese curso.",
            tags = {"productos", "vendedor", "curso", "varios", "filtro", "auth_usuario", "get"}
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
    @GetMapping("/seller/{sellerId}/course/{courseId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCurso(
            @PathVariable Integer sellerId, @PathVariable Integer courseId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCurso(sellerId, courseId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por la ID de un vendedor y carrera",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID y el ID de una carrera." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos de un vendedor para esa carrera.",
            tags = {"productos", "vendedor", "carrera", "varios", "filtro", "auth_usuario", "get"}
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
    @GetMapping("/seller/{sellerId}/career/{careerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCarrera(
            @PathVariable Integer sellerId, @PathVariable Integer careerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCarrera(sellerId, careerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por la ID de un vendedor y categoría",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID y el ID de una categoría." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos de un vendedor para esa categoría.",
            tags = {"productos", "usuario", "vendedor", "categoría", "varios", "filtro", "auth_usuario", "get"}
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
    @GetMapping("/seller/{sellerId}/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCategoria(
            @PathVariable Integer sellerId, @PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCategoria(sellerId, categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05

    @Operation(
            summary = "Sumar una vista al producto",
            description = "Cada vez que un usuario visualiza un producto, se incrementa en 1 el contador de vistas.",
            tags = {"productos", "vista", "id", "auth_usuario", "put"}
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
            tags = {"productos", "vistas", "vendedor", "varios", "todos", "auth_usuario", "get"}
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
            tags = {"productos", "carrera", "vistas", "varios", "recomendados", "auth_usuario", "get"}
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
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosRecomendados(idCareer);
        if (productos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

}
