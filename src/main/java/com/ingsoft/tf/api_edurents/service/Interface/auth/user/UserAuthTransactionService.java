package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;

import java.util.List;

public interface UserAuthTransactionService {

    // HU13

    ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO);
    void cancelarTransaccion(Integer id);

    ShowTransactionDTO obtenerTransaccionPorIdPorUsuario(Integer idTransaction, Integer idUsuario);

    // HU14

    ShowTransactionDTO confirmarEntregaPago(Integer idTransaccion);
    ShowTransactionDTO reclamarTransaccion(Integer id, ClaimTransactionDTO dto);
    ShowTransactionDTO obtenerTransaccionPorProductoYUsuario(Integer idProducto, Integer idUsuario);


    // HU15

    List<ShowTransactionDTO> obtenerTransaccionesPorUsuario(Integer idUser);
    List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(Integer idUser, TransactionStatus estado);
    List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPago(Integer idUser, PaymentMethod metodo);
    List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(Integer idUser, PaymentMethod metodo, TransactionStatus estado);


    void actualizarStockProductos(Integer idProducto);

}
