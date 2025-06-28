package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminProductService;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthProductService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name="Producto_Vendedor", description = "API de Gestion de Productos para un vendedor registrado")
@RestController
@RequestMapping("/seller/auth/products")
@PreAuthorize("hasAnyRole('SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class SellerAuthProductController {

    private final SellerAuthProductService sellerAuthProductService;

    // HU 01
    @Operation(
            summary = "Publicar un producto",
            description = "Permite a un vendedor registrado publicar un nuevo producto en la plataforma. " +
                    "Se devuelve un objeto ShowProductDTO con los detalles del producto creado, como su ID, nombre, " +
                    "descripción, precio y estado de disponibilidad. ",
            tags = {"productos", "auth_vendedor", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "400",
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
            tags = {"productos", "id", "auth_vendedor", "put"}
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
            tags = {"productos", "id", "auth_vendedor", "delete"}
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

    // HU10


    // HU10 - Endpoint 01 (parte 2): Actualizar stock
    @Operation(summary = "Actualizar stock disponible",
            description = "Permite actualizar manualmente la cantidad disponible del producto." +
                    "Se devuelve el producto actualizado con nuevo stock",
            tags = {"productos", "stock", "id", "auth_vendedor", "put"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad negativa no permitida"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/update-stock")
    public ShowProductDTO actualizarCantidadDisponible(@PathVariable Integer id, @RequestBody Integer cantidad) {
        return sellerAuthProductService.actualizarCantidadDisponible(id, cantidad);
    }



    // HU10 - Endpoint 02 (PARTE 2): Actualizar fecha de expiración
    @Operation(summary = "Actualizar fecha de expiración del producto",
            description = "Permite modificar la fecha de expiración de la oferta del producto." +
                    "Se devuelve el producto actualizado con nueva fecha de expiracion",
            tags = {"productos", "fecha-expiracion", "id", "auth_vendedor", "put"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fecha actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> actualizarFechaExpiracion(
            @PathVariable Integer id,
            @RequestBody ProductDTO request) {
        ShowProductDTO updatedProduct = sellerAuthProductService.actualizarFechaExpiracion(id, request.getFecha_expiracion());
        return ResponseEntity.ok(updatedProduct);
    }


}
