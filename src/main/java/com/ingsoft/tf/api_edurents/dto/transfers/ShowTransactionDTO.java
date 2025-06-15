package com.ingsoft.tf.api_edurents.dto.transfers;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowTransactionDTO {
    private Integer id;
    private ShowProductDTO producto;
    private UserDTO usuario;

    @Enumerated(EnumType.STRING)
    private PaymentMethod metodo_pago;

    private LocalDateTime fecha_transaccion;

    @Enumerated(EnumType.STRING)
    private TransactionStatus estado;

    private LocalDateTime fecha_confirmacion_entrega;
    private String motivo_reclamo;

}
