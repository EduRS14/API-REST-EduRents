package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class SellerDTO {
    private Integer id;

    private String presentacion;
    private Boolean confiabilidad;
    private Boolean sin_demoras;
    private Boolean buena_atencion;

    private String nombreNegocio;
    private String correoElectronico;
    private Integer numeroTelefono;

    private String nombreUsuario;
}
