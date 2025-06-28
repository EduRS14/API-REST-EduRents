package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthUserService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "Usuario_GestionUsuario", description = "API de gestion del usuario como usuario ya registrado")
@RestController
@RequestMapping("/user/auth/users")
@PreAuthorize("hasAnyRole('USER', 'SELLER','ADMIN')")
@CrossOrigin(origins = {"http://localhost:4200/", "https://edurents.vercel.app"})
public class UserAuthUserController {

    private final UserAuthUserService userAuthUserService;

    // HU07
    @Operation(summary = "Obtener datos del usuario por su ID",
            description = "Permite al usuario obtener sus datos al ingresar su ID." +
                    "Se devuelve los datos públicos del usuario como su ID, nombre, apellidos, correo, codigo, carrera, ciclo y foto_url",
            tags = {"usuarios", "datos", "id", "auth_usuario","get"}
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
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> obtenerUsuarioPorId(@PathVariable("id") Integer id) {
        UserDTO userDTO = userAuthUserService.obtenerUsuarioPorId(id);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    // HU09
    @Operation(summary = "Cambiar contraseña desde el usuario ya logeado",
            description = "Permite al usuario ya registrado hacer un cambio de contraseña." +
                    "Se devuelve un mensaje de confirmacion validando el cambio de contraseña",
            tags = {"usuarios", "constraseña", "id", "auth_usuario", "put"})
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
    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> cambioContrasena(@PathVariable("id") Integer id, @Valid @RequestBody RecoverPasswordDTO nuevosDatos) {
        UserDTO userDTO = userAuthUserService.cambioContrasenaUsuario(id, nuevosDatos);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    // HU08
    @Operation(summary = "Actualizar datos del usuario ya logeado",
            description = "Permite al usuario ya logeado actualizar sus datos publicos." +
                    "Se devuelve los datos publicos del usuario ya actualizados",
            tags = {"usuarios", "datos", "id", "auth_usuario", "put"})
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
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> actualizarDatosUsuario(@PathVariable("id") Integer id, @Valid @RequestBody UserDTO usuarioDTO) {
        UserDTO updatedUser = userAuthUserService.actualizarDatosUsuario(id, usuarioDTO);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar foto url de perfil del usuario",
            description = "Permite al usuario ya logeado cambiar su foto de perfil via URL." +
                    "Se devuelve la validacion de la foto actualizada",
            tags = {"usuarios", "datos", "foto", "id", "auth_usuario", "patch"})
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
    @PatchMapping("/{id}/photo")
    public ResponseEntity<UserDTO> cambiarFotoUsuario(@PathVariable("id") Integer id, @RequestParam("urlFoto") String urlFoto) {
        UserDTO updatedUser = userAuthUserService.cambiarFotoUsuario(id, urlFoto);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar la carrera del usuario",
            description = "Permite al usuario ya logeado cambiar su carrera." +
                    "Se devuelve la validacion de la carrera actualizada",
            tags = {"usuarios", "datos", "carrera", "id", "auth_usuario", "patch"})
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
    @PatchMapping("/{id}/career")
    public ResponseEntity<UserDTO> cambiarCarreraUsuario(@PathVariable("id") Integer id, @RequestParam("idCarrera") Integer idCarrera) {
        UserDTO updatedUser = userAuthUserService.cambiarCarreraUsuario(id, idCarrera);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar ciclo del usuario",
            description = "Permite al usuario ya logeado cambiar su ciclo actual." +
                    "Se devuelve la validacion del ciclo actualizado",
            tags = {"usuarios", "datos", "ciclo", "id", "auth_usuario", "patch"})
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
    @PatchMapping("/{id}/cycle")
    public ResponseEntity<UserDTO> cambiarCicloUsuario(@PathVariable("id") Integer id, @RequestParam("ciclo") Byte ciclo) {
        UserDTO updatedUser = userAuthUserService.cambiarCicloUsuario(id, ciclo);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

}
