package com.ingsoft.tf.api_edurents.dto.product;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class ShowProductDTO {
    private Integer id;

    //@NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    //@NotBlank(message = "La descripción no puede estar vacía")
    //@Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    private String descripcion;

    //@DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private Double precio;

    //@NotNull(message = "El estado del producto es obligatorio")
    //@Enumerated(EnumType.STRING)
    private ProductStatus estado;

    //@Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private Integer cantidad_disponible;

    //@NotNull(message = "Debe especificarse si acepta intercambio")
    private Boolean acepta_intercambio;

    private LocalDate fecha_creacion;
    private LocalDate fecha_modificacion;
    private LocalDate fecha_expiracion;

    private Integer vistas;

    private SellerDTO vendedor;

    private List<ImageDTO> imagenes;

    private List<CategoryDTO> categorias;

    private List<CourseCareerDTO> cursos_carreras;
}
