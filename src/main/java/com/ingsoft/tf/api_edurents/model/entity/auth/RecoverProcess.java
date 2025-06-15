package com.ingsoft.tf.api_edurents.model.entity.auth;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "procesos_recuperacion")
public class RecoverProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "correo", nullable = false)
    private String correo;

    private String token_original;

    private String token_hasheado;

    private Boolean activado;

    private Boolean valido;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

}
