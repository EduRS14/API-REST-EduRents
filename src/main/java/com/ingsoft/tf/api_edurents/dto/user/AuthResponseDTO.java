package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private Integer id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String codigoUniversitario;
    private String carrera;
    private String fotoUrl;
    private Byte ciclo;
    private String rol;
}
