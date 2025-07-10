package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterSellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthSellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Vendedor_GestionDesdeUsuario",
        description = "API para gestion del perfil del vendedor como usuario"
)
@RestController
@RequestMapping("/user/auth/sellers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class UserAuthSellerController {

    private final UserAuthSellerService sellerService;

    @Operation(summary = "Crear perfil de vendedor si no existe",
            description = "Crea el perfil de vendedor para un usuario autenticado si aún no ha sido creado.",
            tags = {"vendedores", "usuario", "auth_usuario", "post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de vendedor creado o ya existente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("/user/{idUser}")
    public ResponseEntity<SellerDTO> createSeller(@PathVariable Integer idUser,
                                                  @RequestBody(required = false) RegisterSellerDTO registerSellerDTO) {
        return ResponseEntity.ok(sellerService.createSellerIfNotExists(idUser, registerSellerDTO));
    }

    @Operation(summary = "Obtener transacciones de un vendedor",
            description = "Devuelve todas las transacciones realizadas por un vendedor identificado por su ID.",
            tags = {"vendedores", "transaccion", "varios", "todos", "auth_usuario", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado o sin transacciones")
    })
    @GetMapping("/{idSeller}/transactions")
    public ResponseEntity<List<ShowTransactionDTO>> getTransactions(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(sellerService.getTransactionsBySeller(idSeller));
    }
}