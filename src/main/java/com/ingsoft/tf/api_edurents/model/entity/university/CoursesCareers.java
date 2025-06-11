package com.ingsoft.tf.api_edurents.model.entity.university;

import com.ingsoft.tf.api_edurents.model.entity.product.CoursesCareersProduct;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cursos_carreras")
public class CoursesCareers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_curso",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_curso_cursos_carreras"))
    private Course curso;

    @ManyToOne
    @JoinColumn(name = "id_carrera",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_carrera_cursos_carreras"))
    private Career carrera;

    @OneToMany(mappedBy = "curso_carrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoursesCareersProduct> productos = new ArrayList<CoursesCareersProduct>();
}
