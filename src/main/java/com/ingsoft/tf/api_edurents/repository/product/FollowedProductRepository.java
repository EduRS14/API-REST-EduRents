package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowedProductRepository extends JpaRepository<FollowedProduct, Integer> {
}
