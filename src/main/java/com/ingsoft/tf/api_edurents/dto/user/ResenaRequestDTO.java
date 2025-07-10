package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class ResenaRequestDTO {

    private String contenido;
    private Boolean confiabilidad;
    private Boolean sinDemoras;
    private Boolean buenaAtencion;
    private Integer idVendedor;
    private Integer idUsuario;

}
