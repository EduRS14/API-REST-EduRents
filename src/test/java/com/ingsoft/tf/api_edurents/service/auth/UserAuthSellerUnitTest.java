package com.ingsoft.tf.api_edurents.service.auth;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.mapper.SellerMapper;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthSellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserAuthSellerUnitTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SellerMapper sellerMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionsMapper transactionMapper;

    @InjectMocks
    private UserAuthSellerServiceImpl sellerService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1);
    }

    @Test
    @DisplayName("HU11 - CP06: Crear vendedor si no existe")
    void testCreateSellerIfNotExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(sellerRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(sellerRepository.save(any(Seller.class))).thenReturn(new Seller());
        when(sellerMapper.toSellerDTO(any(Seller.class))).thenReturn(new SellerDTO());

        SellerDTO dto = sellerService.createSellerIfNotExists(1);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("HU11 - CP07: Obtener transacciones por vendedor")
    void testGetTransactionsBySeller() {
        when(transactionRepository.findAllBySellerId(1)).thenReturn(Collections.emptyList());

        List<ShowTransactionDTO> transacciones = sellerService.getTransactionsBySeller(1);
        assertNotNull(transacciones);
        assertTrue(transacciones.isEmpty());
    }

    @Test
    @DisplayName("HU11 - CP08: Lanza excepción si el usuario no existe al crear vendedor")
    void testUsuarioNoExisteAlCrearVendedor() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sellerService.createSellerIfNotExists(99));
    }
    @Test
    @DisplayName("HU11 - CP09: No crear vendedor si ya existe")
    void testNoCrearSiYaEsVendedor() {
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(sellerRepository.findByUserId(1L)).thenReturn(Optional.of(new Seller()));
        when(sellerMapper.toSellerDTO(any(Seller.class))).thenReturn(new SellerDTO());

        SellerDTO dto = sellerService.createSellerIfNotExists(1);
        assertNotNull(dto); // ya existía, solo lo retorna
        verify(sellerRepository, never()).save(any());
    }



}
