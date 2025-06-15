package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.AlertDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowAlertDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthAlertServiceImpl;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthFollowedProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Alertas_Usuario", description = "API de gestion de alertas generadas por productos seguidos por el usuario.")
@RestController
@RequestMapping("/user/auth/alerts")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'SELLER')")
public class UserAuthAlertController {

    private final UserAuthAlertServiceImpl alertService;
    private final UserAuthFollowedProductServiceImpl followedProductService;

    @Operation(summary = "Crear alerta para un producto",
            description = "Crea una nueva alerta que vincula a un usuario con un producto seguido.",
            tags = {"alertas", "usuario", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alerta creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario o producto no encontrado")
    })
    @PostMapping
    public ResponseEntity<AlertDTO> createAlert(@RequestBody AlertDTO alertDTO) {
        AlertDTO created = alertService.createAlert(alertDTO);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Eliminar una alerta", description = "Elimina una alerta específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alerta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @DeleteMapping("/{idAlert}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Integer idAlert) {
        alertService.deleteAlert(idAlert);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar alertas de un usuario",
            description = "Permite al usuario observar todas las alertas con las que cuenta." +
                    "Devuelve todas las alertas del usuario ordenadas de más reciente a más antigua.",
            tags = {"alertas", "usuario", "todos", "get"})
    @ApiResponse(responseCode = "200", description = "Alertas obtenidas exitosamente")
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowAlertDTO>> getAlertsByUser(@PathVariable Integer idUser) {
        List<ShowAlertDTO> alerts = alertService.getAlertsByUser(idUser);
        return ResponseEntity.ok(alerts);
    }

    @Operation(summary = "Marcar una alerta como vista",
            description = "Permite al usuario actualiza el estado de un alerta a 'vista'." ,
            tags = {"alertas", "usuario", "vista", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alerta marcada como vista"),
            @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @PutMapping("/{idAlert}/viewed")
    public ResponseEntity<Void> markAsViewed(@PathVariable Integer idAlert) {
        alertService.markAlertAsViewed(idAlert);
        return ResponseEntity.noContent().build();
    }
}