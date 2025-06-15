package com.ingsoft.tf.api_edurents.service.impl.auth.seller;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthTransactionService;
import com.ingsoft.tf.api_edurents.service.impl.admin.AdminProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerAuthTransactionServiceImpl implements SellerAuthTransactionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminProductServiceImpl adminProductService;

    @Autowired
    private final TransactionsMapper transactionsMapper;

    //HU 14

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorProductoYVendedor(Integer idProducto, Integer idVendedor) {
        return transactionRepository.findByProductoIdAndProductoVendedorId(idProducto, idVendedor)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    //HU15

    // Como vendedor
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedor(Integer idSeller) {
        return transactionRepository.findByProducto_Vendedor_Id(idSeller)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorEstado(Integer idSeller, TransactionStatus estado) {
        return transactionRepository.findByProducto_Vendedor_IdAndEstado(idSeller, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPago(Integer idSeller, PaymentMethod metodo) {
        return transactionRepository.findByProducto_Vendedor_IdAndMetodoPago(idSeller, metodo)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorVendedorPorMetodoPagoPorEstado(Integer idSeller, PaymentMethod metodo, TransactionStatus estado) {
        return transactionRepository.findByProducto_Vendedor_IdAndMetodoPagoAndEstado(idSeller, metodo, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }


}
