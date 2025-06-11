package com.ingsoft.tf.api_edurents.model.entity.product;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias_productos")
public class CategoriesProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_categoria",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_categoria_categoria_producto"))
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "id_producto",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_categoria_producto"))
    private Product producto;

}
