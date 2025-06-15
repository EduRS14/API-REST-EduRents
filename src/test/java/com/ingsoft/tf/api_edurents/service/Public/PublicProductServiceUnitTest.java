package com.ingsoft.tf.api_edurents.service.Public;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.model.entity.product.CoursesCareersProduct;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicProductServiceImpl;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
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

    // HU03

    @Test
    @DisplayName("HU01 - CP01 - Obtener todos los productos con datos válidos")
    public void obtenerTodosLosProductos_validData_returnsProducts() {
        Seller seller1 = new Seller();
        seller1.setId(1);

        SellerDTO sellerDTO1 = new SellerDTO();
        sellerDTO1.setId(1);

        Seller seller2 = new Seller();
        seller2.setId(2);

        SellerDTO sellerDTO2 = new SellerDTO();
        sellerDTO2.setId(2);

        Product product1 = new Product();
        product1.setId(1);
        product1.setVendedor(seller1);
        product1.setNombre("Product 1");

        Product product2 = new Product();
        product2.setId(2);
        product2.setVendedor(seller2);
        product2.setNombre("Product 2");

        ShowProductDTO showProductDTO1 = new ShowProductDTO();
        showProductDTO1.setId(1);
        showProductDTO1.setNombre("Product 1");
        showProductDTO1.setVendedor(sellerDTO1);

        ShowProductDTO showProductDTO2 = new ShowProductDTO();
        showProductDTO2.setId(2);
        showProductDTO2.setNombre("Product 2");
        showProductDTO2.setVendedor(sellerDTO2);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toResponse(product1)).thenReturn(showProductDTO1);
        when(productMapper.toResponse(product2)).thenReturn(showProductDTO2);

        List<ShowProductDTO> result = publicProductService.obtenerTodosLosProductos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

    }

    @Test
    @DisplayName("HU01 - CP02 - Obtener todos los productos sin datos")
    public void obtenerTodosLosProductos_empty_returnsEmptyList() {

        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<ShowProductDTO> result = publicProductService.obtenerTodosLosProductos();
        assertTrue(result.isEmpty());
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

    //HU 6
    @Test
    @DisplayName("HU6 - CP01 - Obtener top 10 productos por vistas")
    void obtenerTop10ProductosPorVistas_retornaLista() {
        // Arrange
        Product p1 = new Product(); p1.setId(1); p1.setNombre("Arduino"); p1.setVistas(100);
        Product p2 = new Product(); p2.setId(2); p2.setNombre("Calculadora"); p2.setVistas(90);

        ShowProductDTO dto1 = new ShowProductDTO(); dto1.setId(1); dto1.setNombre("Arduino"); dto1.setVistas(100);
        ShowProductDTO dto2 = new ShowProductDTO(); dto2.setId(2); dto2.setNombre("Calculadora"); dto2.setVistas(90);

        Pageable top10 = PageRequest.of(0, 10);

        when(productRepository.findAllByOrderByVistasDesc(top10)).thenReturn(List.of(p1, p2));
        when(productMapper.toResponse(p1)).thenReturn(dto1);
        when(productMapper.toResponse(p2)).thenReturn(dto2);

        // Act
        List<ShowProductDTO> result = publicProductService.obtenerTop10ProductosPorVistas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Arduino", result.get(0).getNombre());
        assertEquals(100, result.get(0).getVistas());
    }
    @Test
    @DisplayName("HU6 - CP02 - No se encuentran productos, lanza excepción")
    void obtenerTop10ProductosPorVistas_listaVacia_lanzaExcepcion() {
        Pageable top10 = PageRequest.of(0, 10);
        when(productRepository.findAllByOrderByVistasDesc(top10)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> publicProductService.obtenerTop10ProductosPorVistas()
        );

        assertEquals("No se encontraron productos para el top 10 en base a vistas", ex.getMessage());
    }

    @Test
    @DisplayName("HU6 - CP03 - Retorna top 10 productos por cantidad de intercambios")
    void obtenerTop10ProductosPorCantidadDeIntercambios_retornaLista() {
        // Arrange
        Product p1 = new Product(); p1.setId(1); p1.setNombre("Impresora");
        Product p2 = new Product(); p2.setId(2); p2.setNombre("Tablet");

        ShowProductDTO dto1 = new ShowProductDTO(); dto1.setId(1); dto1.setNombre("Impresora");
        ShowProductDTO dto2 = new ShowProductDTO(); dto2.setId(2); dto2.setNombre("Tablet");

        Pageable top10 = PageRequest.of(0, 10);

        when(productRepository.findTopProductsByExchangeOfferCount(top10)).thenReturn(List.of(p1, p2));
        when(productMapper.toResponse(p1)).thenReturn(dto1);
        when(productMapper.toResponse(p2)).thenReturn(dto2);

        // Act
        List<ShowProductDTO> result = publicProductService.obtenerTop10ProductosPorCantidadDeIntercambios();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Impresora", result.get(0).getNombre());
    }

    @Test
    @DisplayName("HU6 - CP04 - No hay productos con intercambios, lanza excepción")
    void obtenerTop10ProductosPorCantidadDeIntercambios_listaVacia_lanzaExcepcion() {
        Pageable top10 = PageRequest.of(0, 10);

        when(productRepository.findTopProductsByExchangeOfferCount(top10)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> publicProductService.obtenerTop10ProductosPorCantidadDeIntercambios()
        );

        assertEquals("No se encontraron productos para el top 10 en base a cantidad de intercambios", ex.getMessage());
    }

    @Test
    @DisplayName("HU6 - CP06 - Retorna top 10 productos más recientes por fecha de creación")
    void obtener10ProductosRecientes_retornaListaCorrecta() {
        // Arrange
        Product p1 = new Product(); p1.setId(1); p1.setNombre("Cuaderno");
        Product p2 = new Product(); p2.setId(2); p2.setNombre("Calculadora");

        ShowProductDTO dto1 = new ShowProductDTO(); dto1.setId(1); dto1.setNombre("Cuaderno");
        ShowProductDTO dto2 = new ShowProductDTO(); dto2.setId(2); dto2.setNombre("Calculadora");

        Pageable top10 = PageRequest.of(0, 10);

        when(productRepository.findAllByOrderByFecha_creacionDesc(top10)).thenReturn(List.of(p1, p2));
        when(productMapper.toResponse(p1)).thenReturn(dto1);
        when(productMapper.toResponse(p2)).thenReturn(dto2);

        // Act
        List<ShowProductDTO> result = publicProductService.obtener10ProductosRecientes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cuaderno", result.get(0).getNombre());
    }

    @Test
    @DisplayName("HU6 - CP06 - No hay productos recientes, lanza excepción")
    void obtener10ProductosRecientes_listaVacia_lanzaExcepcion() {
        Pageable top10 = PageRequest.of(0, 10);

        when(productRepository.findAllByOrderByFecha_creacionDesc(top10)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> publicProductService.obtener10ProductosRecientes()
        );

        assertEquals("No se encontraron productos recientes", ex.getMessage());
    }


}
