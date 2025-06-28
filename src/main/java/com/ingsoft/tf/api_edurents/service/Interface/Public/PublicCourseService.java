package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.product.CourseCareerDTO;
import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;

import java.util.List;

public interface PublicCourseService {

    List<CourseDTO> obtenerCursosPorCarrera(Integer idCarrera);

    List<CourseCareerDTO> obtenerTodosLosCursosConCarreras();
}
