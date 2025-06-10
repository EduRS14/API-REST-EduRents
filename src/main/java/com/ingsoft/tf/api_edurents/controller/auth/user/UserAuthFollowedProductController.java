package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.FollowedProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthFollowedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/followed-products")
public class UserAuthFollowedProductController {

    private final UserAuthFollowedProductService userAuthFollowedProductService;

    //HU 12
    /*
    // Endpoint 05: POST
    @PostMapping("/product/{idProduct}/user/{idUser}/follow")
    public ResponseEntity<Void> followProduct(@PathVariable Integer idProduct, @PathVariable Integer idUser) {
        userAuthFollowedProductService.followProduct(idUser, idProduct);
        return ResponseEntity.ok().build();
    }

    // Endpoint 06: DELETE
    @DeleteMapping("product/{idProduct}/user/{idUser}/follow")
    public ResponseEntity<Void> unfollowProduct(@PathVariable Integer idProduct, @PathVariable Integer idUser) {
        userAuthFollowedProductService.unfollowProduct(idUser, idProduct);
        return ResponseEntity.noContent().build();
    }
    // Endpoint 07: GET
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowProductDTO>> getFollowedProducts(@PathVariable Integer idUser) {
        List<ShowProductDTO> products = userAuthFollowedProductService.getFollowedProductsByUser(idUser);
        return ResponseEntity.ok(products);
    }
    */
     
}
