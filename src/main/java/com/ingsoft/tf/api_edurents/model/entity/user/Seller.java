package com.ingsoft.tf.api_edurents.model.entity.user;

import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "vendedores")
public class Seller {
    @Id
    private Integer id;

    @Column(name = "resena", nullable = false)
    private String resena;

    @Column(name = "confiabilidad", nullable = false)
    private Boolean confiabilidad;

    @Column(name = "sin_demoras", nullable = false)
    private Boolean sin_demoras;

    @Column(name = "buena_atencion", nullable = false)
    private Boolean buena_atencion;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productos = new ArrayList<Product>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_vendedor"))
    private User usuario;
}
