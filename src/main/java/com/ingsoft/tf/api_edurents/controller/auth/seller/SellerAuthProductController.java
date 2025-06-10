package com.ingsoft.tf.api_edurents.controller.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminProductService;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seller/auth/products")
public class SellerAuthProductController {

    private final SellerAuthProductService sellerAuthProductService;

    // HU 01

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowProductDTO> crearProducto(@Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = sellerAuthProductService.crearProducto(productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowProductDTO> editarProducto(@PathVariable Integer id, @Valid @RequestBody ProductDTO productoDTO){
        ShowProductDTO producto = sellerAuthProductService.editarProducto(id, productoDTO);
        return new ResponseEntity<ShowProductDTO>(producto, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarProducto(@PathVariable Integer id){
        sellerAuthProductService.eliminarProducto(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }




}
