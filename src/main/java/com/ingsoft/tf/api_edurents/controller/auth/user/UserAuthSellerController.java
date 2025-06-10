package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/sellers")
public class UserAuthSellerController {

    // HU 11

    private final UserAuthSellerService sellerService;


    // Endpoint 01: Crear vendedor por defecto si no existe
    @PostMapping("/{idUser}")
    public ResponseEntity<SellerDTO> createSeller(@PathVariable Integer idUser) {
        return ResponseEntity.ok(sellerService.createSellerIfNotExists(idUser));
    }


    // Endpoint 07: Transacciones de un vendedor por id
    @GetMapping("/{idSeller}/transactions")
    public ResponseEntity<List<ShowTransactionDTO>> getTransactions(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(sellerService.getTransactionsBySeller(idSeller));
    }

}
