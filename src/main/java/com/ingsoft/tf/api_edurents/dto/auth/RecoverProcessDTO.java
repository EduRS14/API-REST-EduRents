package com.ingsoft.tf.api_edurents.dto.auth;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecoverProcessDTO {
    private Integer id;
    private String correo;
    private String token_original;
    private LocalDateTime fechaExpiracion;
}
