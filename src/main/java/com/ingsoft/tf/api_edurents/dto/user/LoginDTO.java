package com.ingsoft.tf.api_edurents.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDTO {
    @NotNull(message = "El correo no puede ser nulo")
    @Size(min = 5, max = 30)
    private String correo;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 5, max = 30)
    private String contrasena;
}
