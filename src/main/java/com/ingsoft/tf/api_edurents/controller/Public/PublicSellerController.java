package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerReputationDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/sellers")
public class PublicSellerController {
    private final PublicSellerService publicSellerService;

    //HU 11
    @GetMapping("/{idSeller}")
    public ResponseEntity<SellerDTO> getPublicSellerProfile(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(publicSellerService.getPublicSellerProfile(idSeller));
    }

    // Endpoint 04: GET /sellers/name/{name}
    @GetMapping("/name/{name}")
    public ResponseEntity<SellerDTO> getSellerByName(@PathVariable String name) {
        return ResponseEntity.ok(publicSellerService.getSellerByNombreUsuario(name));
    }

    // Endpoint 05: GET /sellers/{idSeller}/reputation
    @GetMapping("/{idSeller}/reputation")
    public ResponseEntity<SellerReputationDTO> getSellerReputation(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(publicSellerService.getSellerReputation(idSeller));
    }

    // Endpoint 06: GET /sellers/{idSeller}/products
    @GetMapping("/{idSeller}/products")
    public ResponseEntity<List<ShowProductDTO>> getSellerProducts(@PathVariable Integer idSeller) {
        return ResponseEntity.ok(publicSellerService.getProductsBySeller(idSeller));
    }


}
