package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.AlertDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowAlertDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthAlertServiceImpl;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthFollowedProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/alerts")
public class UserAuthAlertController {
    private final UserAuthAlertServiceImpl alertService;
    private final UserAuthFollowedProductServiceImpl followedProductService;
    /*
    //HU 12
    // Endpoint 01: POST
    @PostMapping
    public ResponseEntity<AlertDTO> createAlert(@RequestBody AlertDTO alertDTO) {
        AlertDTO created = alertService.createAlert(alertDTO);
        return ResponseEntity.ok(created);
    }

    // Endpoint 02: DELETE
    @DeleteMapping("/{idAlert}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Integer idAlert) {
        alertService.deleteAlert(idAlert);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 03: GET
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ShowAlertDTO>> getAlertsByUser(@PathVariable Integer idUser) {
        List<ShowAlertDTO> alerts = alertService.getAlertsByUser(idUser);
        return ResponseEntity.ok(alerts);
    }

    // Endpoint 04: PUT
    @PutMapping("/{idAlert}/viewed")
    public ResponseEntity<Void> markAsViewed(@PathVariable Integer idAlert) {
        alertService.markAlertAsViewed(idAlert);
        return ResponseEntity.noContent().build();
    }
*/



}
