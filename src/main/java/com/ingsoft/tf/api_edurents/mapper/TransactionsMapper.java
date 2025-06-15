package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionsMapper {

    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public TransactionsMapper(ProductMapper productMapper, UserMapper userMapper) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    public Transaction toEntity(TransactionDTO dto, Product product, User user) {
        Transaction transaction = new Transaction();
        transaction.setMetodoPago(dto.getMetodo_pago());
        transaction.setProducto(product);
        transaction.setUsuario(user);

        transaction.setFecha_transaccion(LocalDateTime.now());
        transaction.setEstado(TransactionStatus.PENDIENTE);
        transaction.setFecha_confirmacion_entrega(null);
        transaction.setMotivo_reclamo(null);

        return transaction;
    }

    public void updateMotivoReclamo(Transaction transaction, ClaimTransactionDTO claim) {
        transaction.setMotivo_reclamo(claim.getMotivo_reclamo());
    }

    public ShowTransactionDTO toResponse(Transaction transaction) {
        ShowTransactionDTO dto = new ShowTransactionDTO();

        dto.setId(transaction.getId());
        dto.setMetodo_pago(transaction.getMetodoPago());
        dto.setFecha_transaccion(transaction.getFecha_transaccion());
        dto.setEstado(transaction.getEstado());
        dto.setFecha_confirmacion_entrega(transaction.getFecha_confirmacion_entrega());
        dto.setMotivo_reclamo(transaction.getMotivo_reclamo());

        dto.setProducto(productMapper.toResponse(transaction.getProducto()));
        dto.setUsuario(userMapper.toResponse(transaction.getUsuario()));

        return dto;
    }

}
