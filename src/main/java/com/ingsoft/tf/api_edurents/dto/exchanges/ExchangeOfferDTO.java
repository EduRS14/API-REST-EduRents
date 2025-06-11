package com.ingsoft.tf.api_edurents.dto.exchanges;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExchangeOfferDTO {

    @NotNull(message = "El id del usuario no puede ser nulo")
    @Positive(message = "El id del usuario debe ser un numero positivo")
    private Integer id_usuario;

    @NotNull(message = "El id del producto no puede ser nulo")
    @Positive(message = "El id del producto debe ser un numero positivo")
    private Integer id_producto;

    @NotNull(message = "El mensaje de la propuesta no puede ser nulo")
    @Size(max = 500, message = "El mensaje de la propuesta no puede exceder los 500 caracteres")
    private String mensaje_propuesta;

    @Enumerated(EnumType.STRING)
    private ExchangeStatus estado;
}
