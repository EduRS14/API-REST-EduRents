package com.ingsoft.tf.api_edurents.model.entity.product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "imagenes")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_producto",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_imagen"))
    private Product producto;

    @Column(name = "url", nullable = false)
    private String url;
}
