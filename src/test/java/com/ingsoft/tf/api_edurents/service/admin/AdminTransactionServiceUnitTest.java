package com.ingsoft.tf.api_edurents.service.admin;

import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AdminTransactionServiceUnitTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionsMapper transactionsMapper;

    @InjectMocks
    private AdminTransactionService adminTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
