package com.ingsoft.tf.api_edurents.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 30 caracteres")
    private String nombres;

    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 30 caracteres")
    private String apellidos;

    @NotNull(message = "El correo no puede ser nulo")
    @Email(message = "El correo no es valido")
    @Size(min = 5, max = 500, message = "El correo debe tener entre 5 y 500 caracteres")
    private String correo;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 5, max = 30, message = "La contraseña debe tener entre 5 y 30 caracteres")
    private String contrasena;

    @NotNull(message = "El id de la carrera no puede ser nulo")
    @Positive(message = "El id de la carrera debe ser un número positivo")
    private Integer id_carrera;

    @NotNull(message = "El codigo universitario no puede ser nulo")
    private String codigo_universitario;

    @NotNull(message = "La foto no puede ser nula")
    private String foto_url;

    @NotNull(message = "El ciclo no puede ser nulo")
    private Byte ciclo;
}
