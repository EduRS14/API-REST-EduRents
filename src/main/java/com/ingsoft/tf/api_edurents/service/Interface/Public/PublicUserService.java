package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;

public interface PublicUserService {

    UserDTO loginUsuario(LoginDTO datosLogin);

    UserDTO registerUsuario(RegisterDTO datosRegistro);

    RecoverProcessDTO forgotPassword(String correo);

    String verifyToken(Integer id, String token);

    void resetPassword(Integer id, String token, String newPassword);

}
