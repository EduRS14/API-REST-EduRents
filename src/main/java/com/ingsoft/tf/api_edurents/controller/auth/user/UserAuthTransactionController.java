package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/transactions")
public class UserAuthTransactionController {

    private final UserAuthTransactionService userAuthTransactionService;

    // HU13

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShowTransactionDTO crearTransaccion(@RequestBody @Valid TransactionDTO transaccionDTO) {
        return userAuthTransactionService.crearTransaccion(transaccionDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelarTransaccion(@PathVariable Integer id){
        userAuthTransactionService.cancelarTransaccion(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Transacción con ID " + id + " cancelada exitosamente.");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowTransactionDTO> getTransaction(@PathVariable Integer id) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionPorId(id));
    }

    @GetMapping
    public List<ShowTransactionDTO> obtenerTransacciones() {
        return userAuthTransactionService.obtenerTransacciones();
    }

    @GetMapping("/{idTransaction}/user/{idUser}")
    public ResponseEntity<ShowTransactionDTO> obtenerPorIdPorUsuario(
            @PathVariable Integer idTransaction,
            @PathVariable Integer idUser) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionPorIdPorUsuario(idTransaction, idUser));
    }

    // HU14

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Map<String, Object>> confirmarEntregaPago(@PathVariable Integer id) {
        ShowTransactionDTO dto = userAuthTransactionService.confirmarEntregaPago(id); // ya no se pasa el estado desde aquí

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Entrega de la transacción " + dto.getId() + " confirmada exitosamente.");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);
    }

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

    @GetMapping("/user/{idUsuario}/product/{idProducto}")
    public ResponseEntity<ShowTransactionDTO> obtenerPorProductoYUsuario(
            @PathVariable Integer idUsuario,
            @PathVariable Integer idProducto) {
        ShowTransactionDTO dto = userAuthTransactionService.obtenerTransaccionPorProductoYUsuario(idProducto, idUsuario);
        return ResponseEntity.ok(dto);
    }


    // HU15

    // Historial como usuario (comprador)
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuario(@PathVariable Integer idUser) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuario(idUser));
    }

    @GetMapping("/user/{idUser}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioYEstado(
            @PathVariable Integer idUser,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuarioPorEstado(idUser, estado));
    }

    @GetMapping("/user/{idUser}/paymentMethod/{metodo}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioYMetodoPago(
            @PathVariable Integer idUser,
            @PathVariable PaymentMethod metodo) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuarioPorMetodoPago(idUser, metodo));
    }

    @GetMapping("/user/{idUser}/paymentMethod/{metodo}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorUsuarioMetodoPagoYEstado(
            @PathVariable Integer idUser,
            @PathVariable PaymentMethod metodo,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(userAuthTransactionService.obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(idUser, metodo, estado));
    }

}
