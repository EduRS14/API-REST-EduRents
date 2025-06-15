package com.ingsoft.tf.api_edurents.dto.transfers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClaimTransactionDTO {

    @NotBlank(message = "Debe ingresar un motivo para el reclamo")
    @Size(min = 10, max = 500, message = "El motivo debe tener entre 10 y 500 caracteres")
    private String motivo_reclamo;

}
