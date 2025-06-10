package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seller/auth/transactions")
public class SellerAuthTransactionController {

    private final SellerAuthTransactionService sellerAuthTransactionService;

    // HU14

    @GetMapping("/seller/{idVendedor}/product/{idProducto}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorProductoYVendedor(
            @PathVariable Integer idVendedor,
            @PathVariable Integer idProducto) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorProductoYVendedor(idProducto, idVendedor));
    }

    // HU15

    // Historial como vendedor
    @GetMapping("/seller/{idSeller}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedor(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedor(idSeller));
    }

    @GetMapping("/seller/{idSeller}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorYEstado(
            @PathVariable Integer idSeller,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedorPorEstado(idSeller, estado));
    }

    @GetMapping("/seller/{idSeller}/paymentMethod/{metodo}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorYMetodoPago(
            @PathVariable Integer idSeller,
            @PathVariable PaymentMethod metodo) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedorPorMetodoPago(idSeller, metodo));
    }

    @GetMapping("/seller/{idSeller}/paymentMethod/{metodo}/state/{estado}")
    public ResponseEntity<List<ShowTransactionDTO>> obtenerPorVendedorMetodoPagoYEstado(
            @PathVariable Integer idSeller,
            @PathVariable PaymentMethod metodo,
            @PathVariable TransactionStatus estado) {
        return ResponseEntity.ok(sellerAuthTransactionService.obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(idSeller, metodo, estado));
    }

}
