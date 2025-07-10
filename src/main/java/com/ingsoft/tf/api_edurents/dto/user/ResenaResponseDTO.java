package com.ingsoft.tf.api_edurents.dto.user;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ResenaResponseDTO {

    private Integer id;
    private String contenido;
    private Boolean confiabilidad;
    private Boolean sinDemoras;
    private Boolean buenaAtencion;
    private String nombreUsuario;
    private LocalDate fecha;

}
