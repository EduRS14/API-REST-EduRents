package com.ingsoft.tf.api_edurents.dto.product;

import com.ingsoft.tf.api_edurents.model.entity.product.AlertType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowAlertDTO {

    private Integer id;

    private AlertType tipo;

    private String mensaje;

    private Boolean visto;

    private LocalDateTime fecha_creacion;

    private Integer id_producto;

    private String nombre_producto;

    private Integer id_usuario;

}
