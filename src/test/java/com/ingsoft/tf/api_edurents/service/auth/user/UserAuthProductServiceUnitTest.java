package com.ingsoft.tf.api_edurents.service.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.CategoryDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Category;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserAuthProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private UserAuthProductServiceImpl userAuthProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // HU03

    @Test
    @DisplayName("HU3 - CP03 - Obtener productos por vendedor con ID existente")
    public void obtenerProductosPorVendedor_validId_returnsProducts() {

        Seller seller = new Seller();
        seller.setId(1);

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(1);

        Product product1 = new Product();
        product1.setId(1);
        product1.setVendedor(seller);
        product1.setNombre("Product 1");

        Product product2 = new Product();
        product2.setId(2);
        product2.setVendedor(seller);
        product2.setNombre("Product 2");

        ShowProductDTO showProductDTO1 = new ShowProductDTO();
        showProductDTO1.setId(1);
        showProductDTO1.setNombre("Product 1");
        showProductDTO1.setVendedor(sellerDTO);

        ShowProductDTO showProductDTO2 = new ShowProductDTO();
        showProductDTO2.setId(2);
        showProductDTO2.setNombre("Product 2");
        showProductDTO2.setVendedor(sellerDTO);

        when(productRepository.findByVendedor(1)).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toResponse(product1)).thenReturn(showProductDTO1);
        when(productMapper.toResponse(product2)).thenReturn(showProductDTO2);

        List<ShowProductDTO> result = userAuthProductService.obtenerProductosPorVendedor(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

    }

    @Test
    @DisplayName("HU3 - CP04 - Obtener productos por vendedor con ID no existente")
    public void obtenerProductosPorVendedor_invalidId_returnsEmptyList() {

        when(productRepository.findByVendedor(999)).thenReturn(Collections.emptyList())
                .thenThrow(new ResourceNotFoundException("No se encontraron productos para el vendedor con id: 999"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthProductService.obtenerProductosPorVendedor(999);
        });
    }

    @Test
    @DisplayName("HU3 - CP04 - Obtener productos por vendedor y categoria con ID existente")
    public void obtenerProductosPorVendedorYCategoria_validId_returnsProducts() {

        Seller seller = new Seller();
        seller.setId(1);

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(1);

        Category category = new Category();
        category.setId(10);
        category.setNombre("Category 1");

        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(10);
        categoryDTO1.setNombre("Category 1");

        Product product1 = new Product();
        product1.setId(1);
        product1.setVendedor(seller);
        product1.setNombre("Product 1");

        CategoriesProducts categoriesProducts1 = new CategoriesProducts();
        categoriesProducts1.setCategoria(category);
        categoriesProducts1.setProducto(product1);

        product1.setCategorias(Collections.singletonList(categoriesProducts1));

        Product product2 = new Product();
        product2.setId(2);
        product2.setVendedor(seller);
        product2.setNombre("Product 2");

        CategoriesProducts categoriesProducts2 = new CategoriesProducts();
        categoriesProducts2.setCategoria(category);
        categoriesProducts2.setProducto(product2);

        product2.setCategorias(Collections.singletonList(categoriesProducts2));

        ShowProductDTO showProductDTO1 = new ShowProductDTO();
        showProductDTO1.setId(1);
        showProductDTO1.setNombre("Product 1");
        showProductDTO1.setVendedor(sellerDTO);
        showProductDTO1.setCategorias(Collections.singletonList(categoryDTO1));

        ShowProductDTO showProductDTO2 = new ShowProductDTO();
        showProductDTO2.setId(2);
        showProductDTO2.setNombre("Product 2");
        showProductDTO2.setVendedor(sellerDTO);
        showProductDTO2.setCategorias(Collections.singletonList(categoryDTO1));

        when(productRepository.findByVendedorAndCategory(1, 10)).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toResponse(product1)).thenReturn(showProductDTO1);
        when(productMapper.toResponse(product2)).thenReturn(showProductDTO2);

        List<ShowProductDTO> result = userAuthProductService.obtenerProductosPorVendedorYCategoria(1, 10);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());

    }

    @Test
    @DisplayName("HU3 - CP06 - Obtener productos por vendedor y categoria con ID no existente")
    public void obtenerProductosPorVendedorYCategoria_invalidId_returnsEmptyList() {

        when(productRepository.findByVendedorAndCategory(1, 999)).thenReturn(Collections.emptyList())
                .thenThrow(new ResourceNotFoundException("No se encontraron productos para el vendedor con id: 1 y categoría con id: 999"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthProductService.obtenerProductosPorVendedorYCategoria(1, 999);
        });
    }

    //HU5
    @Test
    @DisplayName("HU5 - CP01 - Registrar una view por id de producto")
    void registrarViewPorIdDeProducto_returnStatusOK(){
        // Arrange
        Integer productId = 10;
        Product producto = new Product();
        producto.setId(productId);
        producto.setNombre("Lapicero");
        producto.setVistas(0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(producto));

        // Act
        userAuthProductService.sumarView(productId);

        // Assert
        assertEquals(1, producto.getVistas());
        verify(productRepository).save(producto);
    }

    @Test
    @DisplayName("HU5 - CP02 - Producto no encontrado lanza excepción")
    void sumarView_productoNoExiste_lanzaExcepcion() {
        // Arrange
        Integer productId = 999;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userAuthProductService.sumarView(productId)
        );

        assertEquals("Producto no encontrado con id: 999", exception.getMessage());
    }

    @Test
    @DisplayName("HU5 - CP03 - Obtener productos por id de vendedor ordenados por vistas")
    void obtenerProductosPorIdVendedorOrdenarPorVistas_ok() {
        Integer idSeller = 5;

        Product producto = new Product();
        producto.setId(20);
        producto.setNombre("Curso Java");
        producto.setVistas(15);

        ShowProductDTO dto = new ShowProductDTO();
        dto.setId(20);
        dto.setNombre("Curso Java");
        dto.setVistas(15);

        when(productRepository.findBySellerIdOrderByViews(idSeller)).thenReturn(List.of(producto));
        when(productMapper.toResponse(producto)).thenReturn(dto);

        List<ShowProductDTO> result = userAuthProductService.obtenerProductosPorIdVendedorOrdenarPorVistas(idSeller);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Curso Java", result.get(0).getNombre());
        assertEquals(15, result.get(0).getVistas());
    }

    @Test
    @DisplayName("HU5 - CP04 - No hay productos para el vendedor, lanza excepción")
    void obtenerProductosPorIdVendedorOrdenarPorVistas_sinResultados_lanzaExcepcion() {
        Integer idSeller = 999;

        when(productRepository.findBySellerIdOrderByViews(idSeller)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> userAuthProductService.obtenerProductosPorIdVendedorOrdenarPorVistas(idSeller)
        );

        assertEquals("No se encontraron productos del vendedor con id: 999", ex.getMessage());
    }

    @Test
    @DisplayName("HU5 - CP05 - Obtener productos recomendados por id de carrera (ordenados por vistas)")
    void obtenerProductosRecomendados_ok() {
        Integer idCareer = 3;

        Product producto = new Product();
        producto.setId(42);
        producto.setNombre("Arduino para Ingeniería");
        producto.setVistas(80);

        ShowProductDTO dto = new ShowProductDTO();
        dto.setId(42);
        dto.setNombre("Arduino para Ingeniería");
        dto.setVistas(80);

        when(productRepository.findByCareerOrderByViews(idCareer)).thenReturn(List.of(producto));
        when(productMapper.toResponse(producto)).thenReturn(dto);

        List<ShowProductDTO> result = userAuthProductService.obtenerProductosRecomendados(idCareer);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Arduino para Ingeniería", result.get(0).getNombre());
        assertEquals(80, result.get(0).getVistas());
    }
    @Test
    @DisplayName("HU5 - CP06 - No hay productos recomendados para la carrera, lanza excepción")
    void obtenerProductosRecomendados_vacios_lanzaExcepcion() {
        Integer idCareer = 999;

        when(productRepository.findByCareerOrderByViews(idCareer)).thenReturn(Collections.emptyList());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> userAuthProductService.obtenerProductosRecomendados(idCareer)
        );

        assertEquals("No se encontraron productos recomendados para la carrera: " + idCareer, ex.getMessage());
    }

}
