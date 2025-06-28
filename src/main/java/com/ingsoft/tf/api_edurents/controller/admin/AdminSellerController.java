package com.ingsoft.tf.api_edurents.controller.admin;


import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminSellerService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/sellers")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class AdminSellerController {

    @Autowired
    private AdminSellerService sellerService;

    // Ver confiabilidad del vendedor
    @GetMapping("/{id}/confiabilidad")
    public ResponseEntity<SellerDTO> verConfiabilidad(@PathVariable Integer id) {
        SellerDTO sellerDTO = sellerService.getConfiabilidadById(id);
        return new ResponseEntity<SellerDTO>(sellerDTO, HttpStatus.OK);
    }

    // Actualizar confiabilidad del vendedor
    @PutMapping("/{id}/confiabilidad")
    public ResponseEntity<SellerDTO> actualizarConfiabilidad(@PathVariable Integer id, @RequestBody Boolean confiabilidad) {
        SellerDTO sellerDTO = sellerService.updateConfiabilidad(id, confiabilidad);
        return new ResponseEntity<SellerDTO>(sellerDTO, HttpStatus.OK);
    }

}
