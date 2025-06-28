package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.user.AuthResponseDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface PublicUserService {

    AuthResponseDTO loginUsuario(LoginDTO datosLogin);

    ResponseEntity<?> loginGoogle(String idTokenString);

    UserDTO registerUsuario(RegisterDTO datosRegistro);

    RecoverProcessDTO forgotPassword(String correo);

    String verifyToken(Integer id, String token);

    void resetPassword(Integer id, String token, String newPassword);

}
