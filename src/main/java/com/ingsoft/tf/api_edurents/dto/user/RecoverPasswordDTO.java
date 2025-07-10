package com.ingsoft.tf.api_edurents.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecoverPasswordDTO {
    @NotNull(message = "Ingrese un correo válido")
    @Size(min = 5, max = 500, message = "El correo debe tener entre 5 y 500 caracteres")
    private String correo;

    @NotNull(message = "Ingrese una contraseña válida")
    @Size(min = 5, max = 30, message = "La contraseña debe tener entre 5 y 30 caracteres")
    private String contrasena;

    @NotNull(message = "Ingrese una nueva contraseña válida")
    @Size(min = 5, max = 30, message = "La nueva contraseña debe tener entre 5 y 30 caracteres")
    private String nuevaContrasena;
}
