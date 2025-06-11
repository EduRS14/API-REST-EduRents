package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class SellerDTO {
    private Integer id;

    private String resena;
    private Boolean confiabilidad;
    private Boolean sin_demoras;
    private Boolean buena_atencion;

    private String nombreUsuario;
}
