package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.FollowedProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthFollowedProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductoSeguido_Usuario", description = "API de gestion de productos seguidos por usuarios")
@RestController
@RequestMapping("/user/auth/followed-products")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class UserAuthFollowedProductController {

    private final UserAuthFollowedProductService userAuthFollowedProductService;

    @Operation(summary = "Seguir producto",
            description = "El usuario empieza a seguir un producto específico para recibir alertas.",
            tags = {"productos_seguidos", "producto", "usuario", "auth_usuario", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto seguido correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario o producto no encontrado")
    })
    @PostMapping("/product/{idProduct}/user/{idUser}/follow")
    public ResponseEntity<Void> followProduct(@PathVariable Integer idProduct, @PathVariable Integer idUser) {
        userAuthFollowedProductService.followProduct(idUser, idProduct);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Dejar de seguir producto",
            description = "El usuario deja de seguir un producto y deja de recibir alertas relacionadas.",
            tags = {"productos_seguidos", "producto", "usuario", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto dejado de seguir"),
            @ApiResponse(responseCode = "404", description = "El seguimiento no existe")
    })
    @DeleteMapping("product/{idProduct}/user/{idUser}/follow")
    public ResponseEntity<Void> unfollowProduct(@PathVariable Integer idProduct, @PathVariable Integer idUser) {
        userAuthFollowedProductService.unfollowProduct(idUser, idProduct);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los productos seguidos por un usuario",
            description = "Devuelve una lista de productos que el usuario está siguiendo.",
            tags = {"productos_seguidos", "usuario", "varios", "todos", "auth_usuario", "get"})
    @ApiResponse(responseCode = "200", description = "Productos seguidos obtenidos exitosamente")
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowProductDTO>> getFollowedProducts(@PathVariable Integer idUser) {
        List<ShowProductDTO> products = userAuthFollowedProductService.getFollowedProductsByUser(idUser);
        return ResponseEntity.ok(products);
    }
}