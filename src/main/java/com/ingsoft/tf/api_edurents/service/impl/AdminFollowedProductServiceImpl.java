package com.ingsoft.tf.api_edurents.service.impl;

import com.ingsoft.tf.api_edurents.repository.product.FollowedProductRepository;
import com.ingsoft.tf.api_edurents.service.AdminFollowedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFollowedProductServiceImpl implements AdminFollowedProductService {
    @Autowired
    private final FollowedProductRepository followedProductRepository;
}
