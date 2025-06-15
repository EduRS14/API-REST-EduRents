package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.model.entity.product.CoursesCareersProduct;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PublicProductServiceUnitTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private PublicProductServiceImpl publicProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //HU 4
    @Test
    @DisplayName("HU4 - CP03 - Obtener productos por ID de categoria")
    void obtenerProductosPorIdCategoria_returnsListAndStatusOK() {
        // Arrange
        Integer categoryId = 1;

        Category category = new Category();
        category.setId(categoryId);
        category.setNombre("Libros");

        Product product = new Product();
        product.setId(10);
        product.setNombre("Libro Java");

        ShowProductDTO expectedDTO = new ShowProductDTO();
        expectedDTO.setId(10);
        expectedDTO.setNombre("Libro Java");

        // Relación intermedia
        CategoriesProducts cp = new CategoriesProducts();
        cp.setCategoria(category);
        cp.setProducto(product);
        product.setCategorias(List.of(cp));

        // Mocks
        when(productRepository.findByCategoryId(categoryId)).thenReturn(List.of(product));
        when(productMapper.toResponse(product)).thenReturn(expectedDTO);

        // Act
        List<ShowProductDTO> result = publicProductService.obtenerProductosPorCategoria(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Libro Java", result.getFirst().getNombre());
    }

    @Test
    @DisplayName("HU4 - CP04 - No obtener productos por categoria no encontrada")
    void obtenerProductosPorIdCategoria_returnsListAndStatus404() {
        // Arrange
        Integer invalidCategoryId = 999;
        when(productRepository.findByCategoryId(invalidCategoryId)).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> publicProductService.obtenerProductosPorCategoria(invalidCategoryId)
        );

        assertEquals("No se encontraron productos para la categoría con id: 999", thrown.getMessage());
    }

    @Test
    @DisplayName("HU5 - CP05 - Obtener productos por ID de carrera")
    void obtenerProductosPorCarrera_returnsListAndStatusOK() {
        // Arrange
        Integer careerId = 5;

        Career career = new Career();
        career.setId(careerId);
        career.setNombre("Ingeniería");

        Product product = new Product();
        product.setId(100);
        product.setNombre("Calculadora Científica");

        CoursesCareers careerC = new CoursesCareers();
        careerC.setCarrera(career);
        careerC.setId(1);

        CoursesCareersProduct careerC2 = new CoursesCareersProduct();
        careerC2.setCurso_carrera(careerC);
        careerC2.setProducto(product);

        product.setCursos_carreras(List.of(careerC2));

        ShowProductDTO expectedDTO = new ShowProductDTO();
        expectedDTO.setId(100);
        expectedDTO.setNombre("Calculadora Científica");

        when(productRepository.findByCareer(careerId)).thenReturn(List.of(product));
        when(productMapper.toResponse(product)).thenReturn(expectedDTO);

        // Act
        List<ShowProductDTO> result = publicProductService.obtenerProductosPorCarrera(careerId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Calculadora Científica", result.getFirst().getNombre());
    }

    @Test
    @DisplayName("HU5 - CP06 - No obtener productos por carrera no encontrada")
    void obtenerProductosPorCarrera_returnsNotFoundException() {
        // Arrange
        Integer invalidCareerId = 999;
        when(productRepository.findByCareer(invalidCareerId)).thenReturn(Collections.emptyList());

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> publicProductService.obtenerProductosPorCarrera(invalidCareerId)
        );

        assertEquals("No se encontraron productos para la carrera con id: 999", thrown.getMessage());
    }
}
