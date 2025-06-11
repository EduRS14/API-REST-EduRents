package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.CategoryMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.repository.product.CategoryRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado categorías disponibles");
        }
        return categories.stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

}
