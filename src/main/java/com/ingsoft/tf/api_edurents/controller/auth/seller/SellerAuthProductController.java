package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminProductService;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Producto_Vendedor", description = "API de Gestion de Productos para un vendedor registrado")
@RequiredArgsConstructor
@RestController
@RequestMapping("/seller/auth/products")
public class SellerAuthProductController {

    private final SellerAuthProductService sellerAuthProductService;

    // HU 01

    @Operation(
            summary = "Publicar un producto",
            description = "Permite a un vendedor registrado publicar un nuevo producto en la plataforma. " +
                    "Se devuelve un objeto ShowProductDTO con los detalles del producto creado, como su ID, nombre, " +
                    "descripción, precio y estado de disponibilidad. ",
            tags = {"productos", "vendedor", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowProductDTO> crearProducto(@Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = sellerAuthProductService.crearProducto(productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar un producto por su ID",
            description = "Permite a un vendedor registrado actualizar los detalles de un producto específico por su ID. " +
                    "Se devuelve un objeto ShowProductDTO con los detalles del producto, como su nombre, descripción, " +
                    "precio y estado de disponibilidad.",
            tags = {"productos", "vendedor", "put"}
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
    @PutMapping("/{id}")
    public ResponseEntity<ShowProductDTO> editarProducto(@PathVariable Integer id, @Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = sellerAuthProductService.editarProducto(id, productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar un producto por su ID",
            description = "Permite a un vendedor registrado eliminar un producto específico por su ID. " +
                    "No devuelve contenido, pero indica que la operación se realizó con éxito.",
            tags = {"productos", "vendedor", "delete"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    content = { @Content(schema = @Schema()) }
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarProducto(@PathVariable Integer id){
        sellerAuthProductService.eliminarProducto(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }




}
