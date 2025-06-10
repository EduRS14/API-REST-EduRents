package com.ingsoft.tf.api_edurents.controller.Public;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/categories")
public class PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categoryDTOList = publicCategoryService.getAllCategories();
        return new ResponseEntity<List<CategoryDTO>>(categoryDTOList, HttpStatus.OK);
    }
}
