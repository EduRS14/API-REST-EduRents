package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.repository.product.AlertRepository;
import com.ingsoft.tf.api_edurents.service.AdminAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAlertServiceImpl implements AdminAlertService {
    @Autowired
    private final AlertRepository alertRepository;
}
