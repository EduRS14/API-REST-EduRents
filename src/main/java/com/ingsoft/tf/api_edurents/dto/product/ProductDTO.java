package com.ingsoft.tf.api_edurents.dto.product;

import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {

    @NotNull(message = "El id del vendedor no puede ser nulo")
    private Integer id_vendedor;

    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @Size(min = 20, max = 500, message = "La descripción debe tener entre 20 y 500 caracteres")
    private String descripcion;

    @Digits(integer = 5, fraction = 2, message = "El precio debe ser un número con hasta 5 dígitos enteros y 2 decimales")
    private Double precio;

    @Enumerated(EnumType.STRING)
    private ProductStatus estado;

    @PositiveOrZero(message = "La cantidad disponible debe ser un número positivo o cero")
    private Integer cantidad_disponible;

    private Boolean acepta_intercambio;

    @Future(message = "La fecha de expiración debe ser una fecha futura")
    private LocalDate fecha_expiracion;

    private List<@URL(message = "El campo debe ser una URL válida") String> urls_imagenes;

    private List<@Positive(message = "El id de categoría debe ser válido") Integer> categorias;

    private List<@Positive(message = "El id del curso de la carrera debe ser válido") Integer> cursos_carreras;
}