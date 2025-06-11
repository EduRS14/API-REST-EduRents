package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.Transaction;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthTransactionService;
import com.ingsoft.tf.api_edurents.service.impl.admin.AdminProductServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthTransactionServiceImpl implements UserAuthTransactionService {

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

    // HU13

    @Transactional
    @Override
    public ShowTransactionDTO crearTransaccion(TransactionDTO transaccionDTO) {

        User user = userRepository.findById(transaccionDTO.getId_usuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Product product = productRepository.findById(transaccionDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (product.getVendedor().getUsuario().getId().equals(user.getId())) {
            throw new BadRequestException("No puedes realizar una transacción con tu propio producto.");
        }

        if (transactionRepository.existsByProductoIdAndUsuarioId(product.getId(), user.getId())) {
            throw new BadRequestException("Ya realizaste una transacción para este producto.");
        }

        Transaction transaction = new Transaction();
        transaction.setUsuario(user);
        transaction.setProducto(product);
        transaction.setMetodoPago(transaccionDTO.getMetodo_pago());
        transaction.setEstado(TransactionStatus.PENDIENTE);
        transaction.setFecha_transaccion(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        return transactionsMapper.toResponse(transaction);
    }


    @Transactional()
    @Override
    public void cancelarTransaccion(Integer id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaccion no encontrada con id: " + id));

        transaction.setEstado(TransactionStatus.CANCELADO);
        transactionRepository.save(transaction);
    }

    @Transactional()
    @Override
    public ShowTransactionDTO obtenerTransaccionPorId(Integer id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada"));

        return transactionsMapper.toResponse(transaction);
    }

    @Transactional()
    @Override
    public List<ShowTransactionDTO> obtenerTransacciones() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShowTransactionDTO obtenerTransaccionPorIdPorUsuario(Integer idTransaction, Integer idUsuario) {
        Transaction transaction = transactionRepository.findByIdAndUsuarioId(idTransaction, idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la transacción con ese usuario"));
        return transactionsMapper.toResponse(transaction);
    }

    // HU14

    @Transactional()
    @Override
    public ShowTransactionDTO confirmarEntregaPago(Integer idTransaccion) {
        Transaction transaction = transactionRepository.findById(idTransaccion)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada"));

        if (transaction.getEstado() == TransactionStatus.CANCELADO) {
            throw new BadRequestException("No se puede confirmar una transacción cancelada.");
        }

        if (transaction.getEstado() == TransactionStatus.PAGADO) {
            throw new BadRequestException("La entrega ya fue confirmada previamente.");
        }

        transaction.setEstado(TransactionStatus.PAGADO);
        transaction.setFecha_confirmacion_entrega(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);
        return transactionsMapper.toResponse(transaction);
    }


    @Transactional()
    @Override
    public ShowTransactionDTO reclamarTransaccion(Integer id, ClaimTransactionDTO dto) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada"));

        if (transaction.getEstado() == TransactionStatus.CANCELADO) {
            throw new BadRequestException("No se puede reclamar una transacción cancelada.");
        }

        if (transaction.getEstado() == TransactionStatus.PAGADO) {
            throw new BadRequestException("No se puede reclamar una transacción que ya fue confirmada como entregada.");
        }

        if (transaction.getEstado() == TransactionStatus.RECLAMO_ENVIADO) {
            throw new BadRequestException("Ya existe un reclamo enviado para esta transacción.");
        }

        transaction.setEstado(TransactionStatus.RECLAMO_ENVIADO);
        transaction.setMotivo_reclamo(dto.getMotivo_reclamo());
        transaction.setFecha_confirmacion_entrega(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        return transactionsMapper.toResponse(transaction);
    }

    @Override
    public ShowTransactionDTO obtenerTransaccionPorProductoYUsuario(Integer idProducto, Integer idUsuario) {
        Transaction transaction = transactionRepository.findByProductoIdAndUsuarioId(idProducto, idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una transacción para este producto y usuario."));

        return transactionsMapper.toResponse(transaction);
    }

    // HU15

    // Como usuario
    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuario(Integer idUser) {
        return transactionRepository.findByUsuarioId(idUser)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorEstado(Integer idUser, TransactionStatus estado) {
        return transactionRepository.findByUsuarioIdAndEstado(idUser, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPago(Integer idUser, PaymentMethod metodo) {
        return transactionRepository.findByUsuarioIdAndMetodoPago(idUser, metodo)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowTransactionDTO> obtenerTransaccionesPorUsuarioPorMetodoPagoPorEstado(Integer idUser, PaymentMethod metodo, TransactionStatus estado) {
        return transactionRepository.findByUsuarioIdAndMetodoPagoAndEstado(idUser, metodo, estado)
                .stream()
                .map(transactionsMapper::toResponse)
                .collect(Collectors.toList());
    }

}
