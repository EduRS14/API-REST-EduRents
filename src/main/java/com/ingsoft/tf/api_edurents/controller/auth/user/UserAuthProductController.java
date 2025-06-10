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

@Tag(name = "Producto_Usuario", description = "API de gestion de productos para un usuario registrado")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/products")
public class UserAuthProductController {
    private final UserAuthProductService userAuthProductService;

    // HU03

    @Operation(
            summary = "Obtener productos por la ID de un vendedor",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos del vendedor.",
            tags = {"productos", "usuario", "vendedor", "id", "get"}
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
    @GetMapping("/{sellerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedor(@PathVariable Integer sellerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedor(sellerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por la ID de un vendedor y curso",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID y el ID de un curso." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos de un vendedor para ese curso.",
            tags = {"productos", "usuario", "vendedor", "curso", "id", "get"}
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
    @GetMapping("/{sellerId}/course/{courseId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCurso(
            @PathVariable Integer sellerId, @PathVariable Integer courseId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCurso(sellerId, courseId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por la ID de un vendedor y carrera",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID y el ID de una carrera." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos de un vendedor para esa carrera.",
            tags = {"productos", "usuario", "vendedor", "carrera", "id", "get"}
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
    @GetMapping("/{sellerId}/career/{careerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCarrera(
            @PathVariable Integer sellerId, @PathVariable Integer careerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCarrera(sellerId, careerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener productos por la ID de un vendedor y categoría",
            description = "Permite a un usuario registrado obtener una lista de productos ofrecidos por un vendedor específico en base a su ID y el ID de una categoría." +
                    " Se devuelve una lista de objetos ShowProductDTO que representan los productos de un vendedor para esa categoría.",
            tags = {"productos", "usuario", "vendedor", "categoría", "id", "get"}
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
    @GetMapping("/{sellerId}/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCategoria(
            @PathVariable Integer sellerId, @PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCategoria(sellerId, categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05

    @PutMapping("/{idProduct}/views")
    public ResponseEntity<ShowProductDTO> sumarViews(@PathVariable Integer idProduct){
        userAuthProductService.sumarView(idProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/seller/{idSeller}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductSellerOrderByView(@PathVariable Integer idSeller){
        userAuthProductService.obtenerProductosPorIdVendedorOrdenarPorVistas(idSeller);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recomendados/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> recomendarProductos(@PathVariable Integer idCareer){
        userAuthProductService.obtenerProductosRecomendados(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

}
