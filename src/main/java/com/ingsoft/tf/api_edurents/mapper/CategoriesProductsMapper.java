package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import org.springframework.stereotype.Component;

@Component
public class CategoriesProductsMapper {
    private final CategoryMapper categoryMapper;

    public CategoriesProductsMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public CategoryDTO toCategoryDTO(CategoriesProducts categoriesProducts) {
        return categoryMapper.toDTO(categoriesProducts.getCategoria());
    }
}
