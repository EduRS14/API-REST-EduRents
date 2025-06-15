package com.ingsoft.tf.api_edurents.model.entity.product;

import com.ingsoft.tf.api_edurents.model.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "productos_seguidos")
public class FollowedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seguimiento_usuario"))
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false,
            foreignKey = @ForeignKey(name = "fk_seguimiento_producto"))
    private Product producto;

    @Column(name = "fecha_inicio_seguimiento", nullable = false)
    private LocalDate fecha_inicio_seguimiento;
}
