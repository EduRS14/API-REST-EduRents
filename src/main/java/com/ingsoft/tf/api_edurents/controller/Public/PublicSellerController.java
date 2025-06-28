package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerReputationDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicSellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Vendedor_Publico",
        description = "API para gestion publica sobre el vendedor y su confiabilidad."
)
@RestController
@RequestMapping("/public/sellers")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicSellerController {

    private final PublicSellerService publicSellerService;

    @Operation(summary = "Obtener perfil público del vendedor",
            description = "Devuelve la información pública del vendedor por su ID.",
            tags = {"vendedores", "publico", "id", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil del vendedor obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @GetMapping("/{idSeller}")
    public ResponseEntity<SellerDTO> getPublicSellerProfile(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(publicSellerService.getPublicSellerProfile(idSeller));
    }

    @Operation(summary = "Buscar vendedor por nombre completo de usuario",
            description = "Busca el perfil público de un vendedor usando su nombre completo.",
            tags = {"vendedores", "publico", "nombre", "get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<SellerDTO> getSellerByName(@PathVariable String name) {
        return ResponseEntity.ok(publicSellerService.getSellerByNombreUsuario(name));
    }

    @Operation(summary = "Consultar reputación del vendedor",
            description = "Devuelve la reputación del vendedor (confianza, atención, sin demoras).",
            tags = {"vendedores","id","reputacion","publico","get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reputación obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @GetMapping("/{idSeller}/reputation")
    public ResponseEntity<SellerReputationDTO> getSellerReputation(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(publicSellerService.getSellerReputation(idSeller));
    }

    @Operation(summary = "Obtener productos publicados por el vendedor",
            description = "Devuelve una lista de productos publicados por el vendedor con el ID dado.",
            tags = {"vendedor","publico","id","producto","varios","todos","get"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado")
    })
    @GetMapping("/{idSeller}/products")
    public ResponseEntity<List<ShowProductDTO>> getSellerProducts(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(publicSellerService.getProductsBySeller(idSeller));
    }
}