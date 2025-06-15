package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/users")
public class UserAuthUserController {

    private final UserAuthUserService userAuthUserService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> obtenerUsuarioPorId(@PathVariable("id") Integer id) {
        UserDTO userDTO = userAuthUserService.obtenerUsuarioPorId(id);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> cambioContrasena(@PathVariable("id") Integer id, @Valid @RequestBody RecoverPasswordDTO nuevosDatos) {
        UserDTO userDTO = userAuthUserService.cambioContrasenaUsuario(id, nuevosDatos);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> actualizarDatosUsuario(@PathVariable("id") Integer id, @Valid @RequestBody UserDTO usuarioDTO) {
        UserDTO updatedUser = userAuthUserService.actualizarDatosUsuario(id, usuarioDTO);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<UserDTO> cambiarFotoUsuario(@PathVariable("id") Integer id, @RequestParam("urlFoto") String urlFoto) {
        UserDTO updatedUser = userAuthUserService.cambiarFotoUsuario(id, urlFoto);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}/career")
    public ResponseEntity<UserDTO> cambiarCarreraUsuario(@PathVariable("id") Integer id, @RequestParam("idCarrera") Integer idCarrera) {
        UserDTO updatedUser = userAuthUserService.cambiarCarreraUsuario(id, idCarrera);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}/cycle")
    public ResponseEntity<UserDTO> cambiarCicloUsuario(@PathVariable("id") Integer id, @RequestParam("ciclo") Byte ciclo) {
        UserDTO updatedUser = userAuthUserService.cambiarCicloUsuario(id, ciclo);
        return new ResponseEntity<UserDTO>(updatedUser, HttpStatus.OK);
    }

}
