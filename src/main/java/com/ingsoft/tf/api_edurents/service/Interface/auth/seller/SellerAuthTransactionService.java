package com.ingsoft.tf.api_edurents.service.Interface.auth.seller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;

import java.util.List;

public interface SellerAuthTransactionService {

    //HU 14

    List<ShowTransactionDTO> obtenerTransaccionesPorProductoYVendedor(Integer idProducto, Integer idVendedor);

    //HU15

    List<ShowTransactionDTO> obtenerTransaccionesPorVendedor(Integer idSeller);
    List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorEstado(Integer idSeller, TransactionStatus estado);
    List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPago(Integer idSeller, PaymentMethod metodo);
    List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(Integer idSeller, PaymentMethod metodo, TransactionStatus estado);
}
