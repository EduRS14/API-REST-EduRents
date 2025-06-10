package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setNombre(category.getNombre());
        return dto;
    }

    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setNombre(dto.getNombre());
        return category;
    }
}
