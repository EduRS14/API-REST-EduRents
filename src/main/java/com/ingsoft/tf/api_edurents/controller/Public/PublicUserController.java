package com.ingsoft.tf.api_edurents.controller.Public;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.*;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicUserService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Tag(name = "Usuario_Publico", description = "API de gestion del usuario publico")
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class PublicUserController {

    private final PublicUserService publicUserService;

    // HU07
    @Operation(summary = "Registrarse como usuario",
            description = "Permite al estudiante todavia no registrado crear su usuario con datos necesarios para su registro." +
                    "Se devuelve el usuario creado para posteriormente iniciar sesion.",
            tags = {"registrar", "usuarios", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterDTO datosRegistro) {
        UserDTO userDTO = publicUserService.registerUsuario(datosRegistro);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Iniciar sesion",
            description = "Permite al estudiante iniciar sesion para acceder al usuario ya registrado, obtiene token de sesión",
            tags = {"iniciar_sesion", "usuarios", "token", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO datosLogin) {
        AuthResponseDTO userDTO = publicUserService.loginUsuario(datosLogin);
        return new ResponseEntity<AuthResponseDTO>(userDTO, HttpStatus.OK);
    }

    // HU09
    @Operation(summary = "Recuperar contraseña para el estudiante sin iniciar sesion aun",
            description = "Permite al estudiante tener la oportunidad de recuperar su contraseña en caso de haberlo olvidado" +
                    "Se devuelve un token el cual tendrá que verificar",
            tags = {"usuarios", "contraseña", "publico", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<RecoverProcessDTO> forgotPassword(@RequestParam String correo) {
        RecoverProcessDTO response = publicUserService.forgotPassword(correo);
        return new ResponseEntity<RecoverProcessDTO>(response, HttpStatus.OK);
    }

    @Operation(summary = "Verificar token para recuperar contraseña",
            description = "Permite al usuario verificar el token recibido",
            tags = {"usuarios", "contraseña", "token", "verificar", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @PostMapping("/verify-token/{id}")
    public ResponseEntity<Map<String, String>> verifyToken(@PathVariable Integer id, @RequestParam String token) {
        String response = publicUserService.verifyToken(id, token);
        return new ResponseEntity<Map<String, String>>(Map.of("message", response), HttpStatus.OK);
    }

    @Operation(summary = "Reiniciar contraseña desde el token verificado",
            description = "Permite al estudiante reiniciar su contraseña para tener una nueva e iniciar sesion correctamente. " +
                    "Se devuelve la nueva contraseña",
            tags = {"usuarios", "reiniciar", "contraseña", "token", "post"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = ShowProductDTO.class), mediaType = "application/json") }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = { @Content(schema = @Schema())}
            ),
            @ApiResponse(
                    responseCode = "500",
                    content = { @Content(schema = @Schema())}
            )
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/reset-password/{id}")
    public ResponseEntity<Void> resetPassword(@PathVariable Integer id, @RequestParam String token, @RequestParam String newPassword) {
        publicUserService.resetPassword(id, token, newPassword);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> loginConGoogle(@RequestBody GoogleTokenDTO tokenDTO) {
        return publicUserService.loginGoogle(tokenDTO.getIdToken());
    }

}
