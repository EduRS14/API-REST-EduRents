package com.ingsoft.tf.api_edurents.model.entity.user;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "confiabilidad", nullable = false)
    private Boolean confiabilidad;

    @Column(name = "sin_demoras", nullable = false)
    private Boolean sin_demoras;

    @Column(name = "buena_atencion", nullable = false)
    private Boolean buena_atencion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendedor",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_resena_vendedor"), nullable = false)
    private Seller vendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_resena_usuario"), nullable = false)
    private User usuario;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

}
