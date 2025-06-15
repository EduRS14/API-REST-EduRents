package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.model.entity.university.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setNombre(course.getNombre());
        dto.setCodigo(course.getCodigo());
        return dto;
    }
}
