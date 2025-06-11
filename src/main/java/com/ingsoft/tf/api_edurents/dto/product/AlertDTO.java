package com.ingsoft.tf.api_edurents.dto.product;

import com.ingsoft.tf.api_edurents.model.entity.product.AlertType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertDTO {

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer id_usuario;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Integer id_producto;

    @NotNull(message = "El tipo de alerta no puede ser nulo")
    private AlertType tipo;

    @NotBlank(message = "El mensaje de la alerta no puede estar vacío")
    private String mensaje;

}
