package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/courses")
public class PublicCourseController {

    private final PublicCourseService publicCourseService;

    @GetMapping("/career/{id}")
    public ResponseEntity<List<CourseDTO>> obtenerCursosPorCarrera(@PathVariable("id") Integer id){
        List<CourseDTO> cursos = publicCourseService.obtenerCursosPorCarrera(id);
        return new ResponseEntity<List<CourseDTO>>(cursos, HttpStatus.OK);
    }
}
