package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.product.CourseCareerDTO;
import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.CourseMapper;
import com.ingsoft.tf.api_edurents.model.entity.university.Course;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.repository.university.CoursesCareersRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCourseServiceImpl implements PublicCourseService {

    private final CoursesCareersRepository coursesCareersRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CourseDTO> obtenerCursosPorCarrera(Integer idCarrera) {
        List<CoursesCareers> cursosCarreras = coursesCareersRepository.findByCareerId(idCarrera);
        if (cursosCarreras.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron cursos para la carrera con ID: " + idCarrera);
        }

        return cursosCarreras.stream()
                .map(cursoCarrera -> courseMapper.toDTO(cursoCarrera.getCurso()))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseCareerDTO> obtenerTodosLosCursosConCarreras() {
        List<CoursesCareers> cursosCarreras = coursesCareersRepository.findAll();
        if (cursosCarreras.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron cursos con carreras asociadas.");
        }

        return cursosCarreras.stream()
                .map(cursoCarrera -> {
                    CourseCareerDTO courseCareerDTO = new CourseCareerDTO();
                    courseCareerDTO.setId(cursoCarrera.getId());
                    courseCareerDTO.setId_curso(cursoCarrera.getCurso().getId());
                    courseCareerDTO.setCurso(cursoCarrera.getCurso().getNombre());
                    courseCareerDTO.setId_carrera(cursoCarrera.getCarrera().getId());
                    courseCareerDTO.setCarrera(cursoCarrera.getCarrera().getNombre());
                    return courseCareerDTO;
                })
                .toList();
    }
}
