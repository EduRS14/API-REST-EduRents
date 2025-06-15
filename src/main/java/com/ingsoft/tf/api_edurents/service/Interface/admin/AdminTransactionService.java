package com.ingsoft.tf.api_edurents.service.Interface.admin;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;

import java.util.List;

public interface AdminTransactionService {


    ShowTransactionDTO obtenerTransaccionPorId(Integer id);
    List<ShowTransactionDTO> obtenerTransacciones();
}
