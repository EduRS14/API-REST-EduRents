package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class PublicUserController {

    private final PublicUserService publicUserService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO datosRegistro) {
        UserDTO userDTO = publicUserService.registerUsuario(datosRegistro);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO datosLogin) {
        UserDTO userDTO = publicUserService.loginUsuario(datosLogin);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<RecoverProcessDTO> forgotPassword(@RequestParam String correo) {
        RecoverProcessDTO response = publicUserService.forgotPassword(correo);
        return new ResponseEntity<RecoverProcessDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/verify-token/{id}")
    public ResponseEntity<String> verifyToken(@PathVariable Integer id, @RequestParam String token) {
        String response = publicUserService.verifyToken(id, token);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/reset-password/{id}")
    public ResponseEntity<Void> resetPassword(@PathVariable Integer id, @RequestParam String token, @RequestParam String newPassword) {
        publicUserService.resetPassword(id, token, newPassword);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
