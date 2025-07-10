package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class RegisterSellerDTO {
    private String nombreNegocio;
    private String presentacion;
    private String correo;
    private Integer numeroTelefono;
}
