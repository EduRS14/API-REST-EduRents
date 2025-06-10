package com.ingsoft.tf.api_edurents.controller.admin;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    // HU10

    @PutMapping("/{id}/update-stock") //ACTUALIZAR CANTIDAD DE STOCK POR ID DE PRODUCTO
    public ShowProductDTO actualizarCantidadDisponible(@PathVariable Integer id, @RequestBody Integer cantidad) {
        return adminProductService.actualizarCantidadDisponible(id, cantidad);
    }

    @GetMapping("/{id}/stock") //MOSTRAR SOLO LA CANTIDAD DE STOCK POR ID DE PRODUCTO
    public StockDTO obtenerStock(@PathVariable Integer id) {
        return adminProductService.obtenerStockProductoPorId(id);
    }

    @GetMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> obtenerFechaExpiracion(@PathVariable Integer id) {
        ShowProductDTO dto = adminProductService.obtenerFechaExpiracion(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/expiration-date")
    public ResponseEntity<ShowProductDTO> actualizarFechaExpiracion(
            @PathVariable Integer id,
            @RequestBody ProductDTO request) {

        ShowProductDTO updatedProduct = adminProductService.actualizarFechaExpiracion(id, request.getFecha_expiracion());
        return ResponseEntity.ok(updatedProduct);
    }



}
