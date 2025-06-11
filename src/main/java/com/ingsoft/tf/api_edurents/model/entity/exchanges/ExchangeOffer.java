package com.ingsoft.tf.api_edurents.model.entity.exchanges;

import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ofertas_intercambio")
public class ExchangeOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mensaje_propuesta;

    @Enumerated(EnumType.STRING)
    private ExchangeStatus estado;

    @ManyToOne
    @JoinColumn(name = "id_producto",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_producto_transaccion"), nullable = false)
    private Product producto;

    @ManyToOne
    @JoinColumn(name = "id_usuario",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_usuario_transaccion"), nullable = false)
    private User usuario;
}
