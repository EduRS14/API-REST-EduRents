package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/products")
public class UserAuthProductController {
    private final UserAuthProductService userAuthProductService;

    // HU03

    @GetMapping("/{sellerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedor(@PathVariable Integer sellerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedor(sellerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/course/{courseId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCurso(
            @PathVariable Integer sellerId, @PathVariable Integer courseId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCurso(sellerId, courseId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/career/{careerId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCarrera(
            @PathVariable Integer sellerId, @PathVariable Integer careerId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCarrera(sellerId, careerId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/category/{categoryId}")
    public ResponseEntity<List<ShowProductDTO>> obtenerProductosPorVendedorYCategoria(
            @PathVariable Integer sellerId, @PathVariable Integer categoryId) {
        List<ShowProductDTO> productos = userAuthProductService.obtenerProductosPorVendedorYCategoria(sellerId, categoryId);
        return new ResponseEntity<List<ShowProductDTO>>(productos, HttpStatus.OK);
    }

    // HU 05

    @PutMapping("/{idProduct}/views")
    public ResponseEntity<ShowProductDTO> sumarViews(@PathVariable Integer idProduct){
        userAuthProductService.sumarView(idProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/seller/{idSeller}/views")
    public ResponseEntity<List<ShowProductDTO>> ProductSellerOrderByView(@PathVariable Integer idSeller){
        userAuthProductService.obtenerProductosPorIdVendedorOrdenarPorVistas(idSeller);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recomendados/{idCareer}")
    public ResponseEntity<List<ShowProductDTO>> recomendarProductos(@PathVariable Integer idCareer){
        userAuthProductService.obtenerProductosRecomendados(idCareer);
        return new ResponseEntity<List<ShowProductDTO>>(HttpStatus.NO_CONTENT);
    }

}
