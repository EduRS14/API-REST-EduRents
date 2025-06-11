package com.ingsoft.tf.api_edurents.model.entity.user;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class User {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "codigo_universitario", nullable = false)
    private String codigo_universitario;

    @Column(name = "ciclo", nullable = false)
    private Byte ciclo;

    @Column(name = "foto_url", nullable = false)
    private String foto_url;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Seller vendedor;

    @ManyToOne
    @JoinColumn(name = "id_carrera",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_carrera"), nullable = false)
    private Career carrera;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transacciones = new ArrayList<Transaction>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeOffer> intercambios = new ArrayList<ExchangeOffer>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<FollowedProduct> productos_seguidos;

}