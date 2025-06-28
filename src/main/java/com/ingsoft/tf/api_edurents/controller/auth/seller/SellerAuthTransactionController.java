package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthTransactionService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Transaccion_Vendedor", description = "API de Gestion de Transacciones desde el vendedor registrado")
@RestController
@RequestMapping("/seller/auth/transactions")
@PreAuthorize("hasAnyRole('SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class SellerAuthTransactionController {

    private final SellerAuthTransactionService sellerAuthTransactionService;

    // HU14
    @Operation(
            summary = "Obtener lista de transacciones creadas en base a tu producto como vendedor",
            description = "Buscas las transacciones creada en base a tu producto (ID producto) como vendedor registrado (ID vendedor)" +
                    "Se devuelve un objeto de transacción con una ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y su estado actual.",
            tags = {"transacciones", "vendedor", "producto", "varios", "auth_vendedor", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/seller/{idVendedor}/product/{idProducto}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorProductoYVendedor(
            @PathVariable Integer idVendedor,
            @PathVariable Integer idProducto) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorProductoYVendedor(idProducto, idVendedor));
    }

    // HU15

    // Historial como vendedor
    @Operation(
            summary = "Observar historial de transacciones recibidas como vendedor",
            description = "Observas todo tu historial de transacciones recibidas, con tu ID de vendedor" +
                    "Se devuelve una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "vendedor", "varios", "todos", "auth_vendedor", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/seller/{idSeller}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedor(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedor(idSeller));
    }

    @Operation(
            summary = "Filtrar por estado el historial de transacciones recibidas como vendedor",
            description = "Observas todo tu historial de transacciones recibidas, con tu ID de vendedor, y filtro por estado" +
                    "Se devuelve una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "vendedor", "estado", "varios", "filtro", "auth_vendedor", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/seller/{idSeller}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorYEstado(
            @PathVariable Integer idSeller,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedorPorEstado(idSeller, estado));
    }

    @Operation(
            summary = "Filtrar por metodo de pago el historial de transacciones recibidas como vendedor",
            description = "Observas todo tu historial de transacciones recibidas, con tu ID de vendedor, y filtro por metodo de pago" +
                    "Se devuelve una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "vendedor", "metodo", "varios", "filtro", "auth_vendedor", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/seller/{idSeller}/paymentMethod/{metodo}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorYMetodoPago(
            @PathVariable Integer idSeller,
            @PathVariable PaymentMethod metodo) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedorPorMetodoPago(idSeller, metodo));
    }

    @Operation(
            summary = "Filtrar por metodo de pago y estado el historial de transacciones recibidas como vendedor",
            description = "Observas todo tu historial de transacciones recibidas, con tu ID de vendedor, y filtro por metodo de pago y estado" +
                    "Se devuelve una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "vendedor", "metodo", "estado", "varios", "filtro", "auth_vendedor", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/seller/{idSeller}/paymentMethod/{metodo}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorMetodoPagoYEstado(
            @PathVariable Integer idSeller,
            @PathVariable PaymentMethod metodo,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(idSeller, metodo, estado));
    }

}
