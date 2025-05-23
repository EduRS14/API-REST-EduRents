package com.ingsoft.tf.api_edurents.controller;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO datosRegistro) {
        UserDTO userDTO = adminUserService.registerUsuario(datosRegistro);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> cambioContrasena(@PathVariable("id") Integer id, @Valid @RequestBody RecoverPasswordDTO nuevosDatos) {
        UserDTO userDTO = adminUserService.cambioContrasenaUsuario(id, nuevosDatos);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO datosLogin) {
        UserDTO userDTO = adminUserService.loginUsuario(datosLogin);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

}
