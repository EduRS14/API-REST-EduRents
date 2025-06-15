package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.CourseCareerDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.CoursesCareersProduct;
import org.springframework.stereotype.Component;

@Component
public class CourseCareerProductMapper {
    public CourseCareerDTO toResponse(CoursesCareersProduct coursesCareersProduct) {
        CourseCareerDTO dto = new CourseCareerDTO();

        dto.setId_curso(coursesCareersProduct.getCurso_carrera().getCurso().getId());
        dto.setCurso(coursesCareersProduct.getCurso_carrera().getCurso().getNombre());

        dto.setId_carrera(coursesCareersProduct.getCurso_carrera().getCarrera().getId());
        dto.setCarrera(coursesCareersProduct.getCurso_carrera().getCarrera().getNombre());

        return dto;
    }
}
