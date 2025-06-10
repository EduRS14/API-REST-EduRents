package com.ingsoft.tf.api_edurents.controller.admin;

import com.ingsoft.tf.api_edurents.dto.transfers.ClaimTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.transfers.TransactionDTO;
import com.ingsoft.tf.api_edurents.model.entity.transfers.PaymentMethod;
import com.ingsoft.tf.api_edurents.model.entity.transfers.TransactionStatus;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/transactions")
public class AdminTransactionController {

    private final AdminTransactionService adminTransactionService;






}
