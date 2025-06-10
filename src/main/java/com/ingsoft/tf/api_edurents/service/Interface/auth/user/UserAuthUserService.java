package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;

public interface UserAuthUserService {

    UserDTO obtenerUsuarioPorId(Integer id);

    UserDTO cambioContrasenaUsuario(Integer id, RecoverPasswordDTO nuevosDatos);

    UserDTO actualizarDatosUsuario(Integer id, UserDTO usuarioDTO);

    UserDTO cambiarFotoUsuario(Integer id, String urlFoto);

    UserDTO cambiarCarreraUsuario(Integer id, Integer idCarrera);

    UserDTO cambiarCicloUsuario(Integer id, Byte ciclo);
}
