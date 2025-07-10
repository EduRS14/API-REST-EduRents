package com.ingsoft.tf.api_edurents.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDTO {
    @NotNull(message = "El correo no puede ser nulo")
    @NotBlank(message = "El correo es obligatorio")
    @Size(min = 5, max = 500)
    private String correo;

    @NotNull(message = "La contraseña no puede ser nula")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 5, max = 30)
    private String contrasena;
}
