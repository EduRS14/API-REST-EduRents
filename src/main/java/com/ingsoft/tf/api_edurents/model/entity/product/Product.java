package com.ingsoft.tf.api_edurents.model.entity.product;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "productos")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Enumerated(EnumType.STRING)
    private ProductStatus estado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fecha_creacion;

    @Column(name = "fecha_modificacion")
    private LocalDate fecha_modificacion;

    @Column(name = "fecha_expiracion")
    private LocalDate fecha_expiracion;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidad_disponible;

    @Column(name = "acepta_intercambio", nullable = false)
    private Boolean acepta_intercambio;

    @Column(name = "vistas")
    private Integer vistas = 0;

    @ManyToOne
    @JoinColumn(name = "id_vendedor",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_categoria"), nullable = false)
    private Seller vendedor;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imagenes = new ArrayList<Image>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoriesProducts> categorias = new ArrayList<CategoriesProducts>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoursesCareersProduct> cursos_carreras = new ArrayList<CoursesCareersProduct>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transacciones = new ArrayList<Transaction>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeOffer> intercambios = new ArrayList<ExchangeOffer>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<FollowedProduct> usuarios_siguiendo;

}