package com.ingsoft.tf.api_edurents.repository.university;

import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Integer> {
}
