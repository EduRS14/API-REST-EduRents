package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String codigo_universitario;
    private String carrera;
    private Byte ciclo;
}