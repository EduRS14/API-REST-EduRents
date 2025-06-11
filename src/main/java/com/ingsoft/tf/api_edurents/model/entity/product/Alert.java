package com.ingsoft.tf.api_edurents.model.entity.product;

import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "alertas")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false,
            foreignKey = @ForeignKey(name = "fk_alerta_usuario"))
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false,
            foreignKey = @ForeignKey(name = "fk_alerta_producto"))
    private Product producto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private AlertType tipo;

    @Column(name = "mensaje", nullable = false, length = 255)
    private String mensaje;

    @Column(name = "visto", nullable = false)
    private Boolean visto = false;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fecha_creacion = LocalDateTime.now();
}
