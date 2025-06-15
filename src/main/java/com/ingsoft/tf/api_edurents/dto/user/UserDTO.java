package com.ingsoft.tf.api_edurents.dto.user;

import com.ingsoft.tf.api_edurents.model.entity.user.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String correo;
    private UserRole rol;
    private String codigo_universitario;
    private String carrera;
    private String foto_url; // agregado para el test
    private Byte ciclo;
}