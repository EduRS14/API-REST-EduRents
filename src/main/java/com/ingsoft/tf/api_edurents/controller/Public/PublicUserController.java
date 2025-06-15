package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.AuthResponseDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
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

@RequiredArgsConstructor
@Tag(name = "Usuario_Publico", description = "API de gestion del usuario publico")
@RestController
@RequestMapping("/auth")
public class PublicUserController {

    private final PublicUserService publicUserService;

    // HU07
    @Operation(summary = "Registrarse como usuario",
            description = "Permite al estudiante todavia no registrado crear su usuario con datos necesarios para su registro." +
                    "Se devuelve el usuario creado para posteriormente iniciar sesion.",
            tags = {"registrar", "usuario", "post"}
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
            description = "Permite al estudiante iniciar sesion para acceder al usuario ya registrado",
            tags = {"iniciar_sesion", "usuario", "post"}
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
            tags = {"recuperar_contra", "publico", "post"}
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
            tags = {"verificar", "token", "post"}
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
    public ResponseEntity<String> verifyToken(@PathVariable Integer id, @RequestParam String token) {
        String response = publicUserService.verifyToken(id, token);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @Operation(summary = "Reiniciar contraseña desde el token verificado",
            description = "Permite al estudiante reiniciar su contraseña para tener una nueva e iniciar sesion correctamente. " +
                    "Se devuelve la nueva contraseña",
            tags = {"verificar", "token", "post"}
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
}
