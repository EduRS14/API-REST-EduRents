package com.ingsoft.tf.api_edurents.model.entity.product;

import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cursos_carreras_productos")
public class CoursesCareersProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_producto",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_cursos_carreras_productos"), nullable = false)
    private Product producto;

    @ManyToOne
    @JoinColumn(name = "id_curso_carrera",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_curso_carrera_cursos_carreras_productos"), nullable = false)
    private CoursesCareers curso_carrera;
}
