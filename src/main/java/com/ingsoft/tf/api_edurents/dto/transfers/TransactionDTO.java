package com.ingsoft.tf.api_edurents.dto.transfers;

import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionDTO {

    @NotNull(message = "Siempre debe haber un producto")
    private Integer id_producto;
    @NotNull(message = "Siempre debe haber un usuario")
    private Integer id_usuario;

    @NotNull(message = "Debes elegir un metodo de pago")
    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo_pago;
    
}
