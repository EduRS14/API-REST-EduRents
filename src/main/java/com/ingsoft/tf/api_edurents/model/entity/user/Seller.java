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

    @Column(name = "nombre_negocio", nullable = false)
    private String nombre_negocio;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombre_usuario;

    @Column(name = "presentacion", nullable = false)
    private String presentacion;

    @Column(name = "numero_telefono", nullable = false)
    private Integer numero_telefono;

    @Column(name = "correo_electronico", nullable = false)
    private String correo_electronico;

    @Column(name = "confiabilidad", nullable = false)
    private Boolean confiabilidad;

    @Column(name = "sin_demoras", nullable = false)
    private Boolean sin_demoras;

    @Column(name = "buena_atencion", nullable = false)
    private Boolean buena_atencion;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productos = new ArrayList<Product>();

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resena> resenas = new ArrayList<Resena>();

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_vendedor"))
    private User usuario;
}
