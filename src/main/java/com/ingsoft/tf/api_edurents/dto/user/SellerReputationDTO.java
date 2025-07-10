package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

@Data
public class SellerReputationDTO {
    private String presentacion;
    private Boolean confiabilidad;
    private Boolean sin_demoras;
    private Boolean buena_atencion;
}