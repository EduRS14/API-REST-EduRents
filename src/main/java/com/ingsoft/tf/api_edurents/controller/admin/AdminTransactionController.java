package com.ingsoft.tf.api_edurents.controller.admin;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Tag(name = "Transaccion_Administracion", description = "API de Gestion de Transacciones desde la administracion")
@RequestMapping("admin/transactions")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;

    @Operation(
            summary = "Obtener transaccion por ID",
            description = "Buscas y obtienes una transaccion por su ID." +
                    "Se devuelve un objeto de transacción con una ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y su estado actual.",
            tags = {"transacciones", "id", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShowTransactionDTO> getTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(adminTransactionService.obtenerTransaccionPorId(id));
    }

    @Operation(
            summary = "Obtener todas las transacciones",
            description = "Obtienes todas las transacciones creadas en la plataforma" +
                    "Se devuelve una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "todos", "admin", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<ShowTransactionDTO> obtenerTransacciones() {
        return adminTransactionService.obtenerTransacciones();
    }

}
