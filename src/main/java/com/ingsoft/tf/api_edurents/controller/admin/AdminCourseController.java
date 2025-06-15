package com.ingsoft.tf.api_edurents.controller.admin;

import com.ingsoft.tf.api_edurents.dto.university.CourseDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminCourseService;
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
@RequestMapping("admin/courses")
public class AdminCourseController {

    private final AdminCourseService adminCourseService;

}
