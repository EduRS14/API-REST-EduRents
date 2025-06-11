package com.ingsoft.tf.api_edurents.repository.university;

import com.ingsoft.tf.api_edurents.model.entity.university.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
