package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthTransactionService;
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
@Tag(name = "Transaccion_Usuario", description = "API de Gestion de Transacciones desde el usuario registrado")
@RestController
@RequestMapping("/user/auth/transactions")
@PreAuthorize("hasAnyRole('USER', 'SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class UserAuthTransactionController {

    private final UserAuthTransactionService userAuthTransactionService;

    // HU13

    @Operation(
            summary = "Crear una transaccion con metodo de pago seguro",
            description = "Creas una transaccion a partir de un producto elegido, registrando tu ID de usuario. " +
                    "En la entrada, además de ingresar la ID del producto y usuario, eliges el metodo de pago y se guarda el monto en la transacción hasta confirmar la entrega. " +
                    "La respuesta es un objeto de transacción con una ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y su estado actual.",
            tags = {"transacciones", "usuario", "auth_usuario", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowTransactionDTO crearTransaccion(@RequestBody @Valid TransactionDTO transaccionDTO) {
        return userAuthTransactionService.crearTransaccion(transaccionDTO);
    }


    @Operation(
            summary = "Cancelar una transaccion por ID",
            description = "Eliges la transaccion que deseas cancelar en base a su ID, y el monto volverá a la cuenta del usuario. " +
                    "La respuesta es un mensaje confirmando que la transaccion fue cancelada exitosamente",
            tags = {"transacciones", "usuario", "id", "auth_usuario", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelarTransaccion(@PathVariable Integer id){
        userAuthTransactionService.cancelarTransaccion(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Transacción con ID " + id + " cancelada exitosamente.");

        return ResponseEntity.ok(response);
    }




    @Operation(
            summary = "Buscar tu transaccion creada como usuario",
            description = "Buscas una transaccion creada en base a su ID y tu ID de usuario." +
                    "La respuesta es un objeto de transacción con una ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y su estado actual.",
            tags = {"transacciones", "usuario", "id", "auth_usuario", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{idTransaction}/user/{idUser}")
    public ResponseEntity<ShowTransactionDTO> obtenerPorIdPorUsuario(
            @PathVariable Integer idTransaction,
            @PathVariable Integer idUser) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionPorIdPorUsuario(idTransaction, idUser));
    }

    // HU14

    @Operation(
            summary = "Confirmar entrega de producto en la transaccion por ID",
            description = "Eliges la transaccion en la que deseas confirmar la entrega del producto, en base a su ID, y el monto será enviado a la cuenta del vendedor. " +
                    "La respuesta es un mensaje confirmando que la entrega fue confirmada exitosamente",
            tags = {"transacciones", "usuario", "id", "auth_usuario", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Map<String, Object>> confirmarEntregaPago(@PathVariable Integer id) {
        ShowTransactionDTO dto = userAuthTransactionService.confirmarEntregaPago(id); // ya no se pasa el estado desde aquí

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Entrega de la transacción " + dto.getId() + " confirmada exitosamente.");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Reclamar una transaccion por ID",
            description = "Eliges la transaccion en la que deseas hacer el reclamo en base a su ID. " +
                    "La entrada es el motivo del reclamo. La respuesta es un mensaje confirmando que la transaccion fue reclamada",
            tags = {"transacciones", "usuario", "id", "auth_usuario", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}/claim")
    public ResponseEntity<Map<String, Object>> reclamarTransaccion(
            @PathVariable Integer id,
            @Valid @RequestBody ClaimTransactionDTO dto
    ) {
        ShowTransactionDTO updated = userAuthTransactionService.reclamarTransaccion(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reclamo registrado exitosamente en la transacción " + updated.getId());
        response.put("transaccion", updated);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener transaccion como usuario en base a la ID del producto",
            description = "Buscas tu transaccion creada en base a un producto (ID producto) con tu usuario registrado (ID usuario)" +
                    "La respuesta es un objeto de transacción con una ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y su estado actual.",
            tags = {"transacciones", "usuario", "producto", "id", "auth_usuario", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ShowTransactionDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/user/{idUsuario}/product/{idProducto}")
    public ResponseEntity<ShowTransactionDTO> obtenerPorProductoYUsuario(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {
        ShowTransactionDTO dto = userAuthTransactionService.obtenerTransaccionPorProductoYUsuario(idProducto, idUsuario);
        return ResponseEntity.ok(dto);
    }


    // HU15

    // Historial como usuario (comprador)

    @Operation(
            summary = "Observar historial de tus transacciones como usuario",
            description = "Observas todo tu historial de transacciones creadas, con tu ID de usuario" +
                    "La respuesta es una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "usuario", "varios", "todos", "auth_usuario", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuario(@PathVariable Integer idUser) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuario(idUser));
    }

    @Operation(
            summary = "Filtrar por estado el historial de tus transacciones como usuario",
            description = "Observas todo tu historial de transacciones creadas, con tu ID de usuario, con filtro por estado" +
                    "La respuesta es una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "usuario", "estado", "varios", "filtro", "auth_usuario", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/user/{idUser}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioYEstado(
            @PathVariable Integer idUser,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuarioPorEstado(idUser, estado));
    }

    @Operation(
            summary = "Filtrar por metodo de pago el historial de tus transacciones como usuario",
            description = "Observas todo tu historial de transacciones creadas, con tu ID de usuario, con filtro por metodo de pago" +
                    "La respuesta es una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "usuario", "metodo", "varios", "filtro", "auth_usuario", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/user/{idUser}/paymentMethod/{metodo}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioYMetodoPago(
            @PathVariable Integer idUser,
            @PathVariable PaymentMethod metodo) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuarioPorMetodoPago(idUser, metodo));
    }

    @Operation(
            summary = "Filtrar por metodo de pago y estado el historial de tus transacciones como usuario",
            description = "Observas todo tu historial de transacciones creadas, con tu ID de usuario, con filtro por metodo de pago y estado" +
                    "La respuesta es una lista de transacciones con ID, ID de producto, ID de usuario, metodo de pago, fecha transaccion, y estado actual. ",
            tags = {"transacciones", "usuario", "metodo", "estado", "varios", "filtro", "auth_usuario", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ShowTransactionDTO.class))),}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/user/{idUser}/paymentMethod/{metodo}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioMetodoPagoYEstado(
            @PathVariable Integer idUser,
            @PathVariable PaymentMethod metodo,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(idUser, metodo, estado));
    }

}
