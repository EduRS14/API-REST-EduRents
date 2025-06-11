package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
}
