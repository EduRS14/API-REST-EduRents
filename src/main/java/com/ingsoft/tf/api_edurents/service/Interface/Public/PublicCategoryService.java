package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;

import java.util.List;

public interface PublicCategoryService {

    List<CategoryDTO> getAllCategories();
}
