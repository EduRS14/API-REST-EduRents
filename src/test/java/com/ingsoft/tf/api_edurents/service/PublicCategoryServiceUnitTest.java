package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.controller.Public.PublicCategoryController;
import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.CategoryMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.repository.product.CategoryRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCategoryService;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class PublicCategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private PublicCategoryServiceImpl publicCategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //HU 4
    @Test
    @DisplayName("HU4 - CP01 - Encontrar lista de categorias")
    void testGetAllCategories_returnsListAndStatusOK() {
        // Arrange: crea entidades simuladas
        Category category1 = new Category();
        category1.setId(1);
        category1.setNombre("Libros");

        Category category2 = new Category();
        category2.setId(2);
        category2.setNombre("Electrónica");

        List<Category> categories = Arrays.asList(category1, category2);

        // Crea DTOs simulados
        CategoryDTO dto1 = new CategoryDTO();
        dto1.setId(1);
        dto1.setNombre("Libros");

        CategoryDTO dto2 = new CategoryDTO();
        dto2.setId(2);
        dto2.setNombre("Electrónica");

        // Mocks
        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDTO(category1)).thenReturn(dto1);
        when(categoryMapper.toDTO(category2)).thenReturn(dto2);

        // Act
        List<CategoryDTO> result = publicCategoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Libros", result.get(0).getNombre());
        assertEquals("Electrónica", result.get(1).getNombre());
    }

    @Test
    @DisplayName("HU4 - CP02 - Lista de categorias vacia")
    void testGetAllCategories_returnsEmptyList() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            publicCategoryService.getAllCategories();
        });
    }
}

