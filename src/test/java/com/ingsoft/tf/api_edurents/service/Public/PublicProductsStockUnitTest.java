package com.ingsoft.tf.api_edurents.service.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PublicProductsStockUnitTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private PublicProductServiceImpl productService;

    private Product mockProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockProduct = new Product();
        mockProduct.setId(1);
        mockProduct.setCantidad_disponible(5);
        mockProduct.setFecha_expiracion(LocalDate.of(2025, 12, 31));
        mockProduct.setEstado(ProductStatus.USADO);
        mockProduct.setAcepta_intercambio(true);
    }

    @Test
    @DisplayName("HU10 - CP01: Obtener stock disponible por ID")
    void testObtenerStockProductoPorId() {
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        StockDTO stock = productService.obtenerStockProductoPorId(1);

        assertNotNull(stock);
        assertEquals(5, stock.getCantidad_disponible());
        assertEquals(1, stock.getId());
    }

    @Test
    @DisplayName("HU10 - CP02: Obtener fecha de expiración por ID")
    void testObtenerFechaExpiracion() {
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        ShowProductDTO dto = productService.obtenerFechaExpiracion(1);

        assertNotNull(dto);
        assertEquals(LocalDate.of(2025, 12, 31), dto.getFecha_expiracion());
    }

    @Test
    @DisplayName("HU10 - CP03: Obtener estado del producto por ID")
    void testObtenerEstado() {
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        ShowProductDTO dto = productService.obtenerEstado(1);

        assertNotNull(dto);
        assertEquals(ProductStatus.USADO, dto.getEstado());
    }

    @Test
    @DisplayName("HU10 - CP04: Saber si acepta intercambio por ID")
    void testObtenerEstadoAceptaIntercambio() {
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        ShowProductDTO dto = productService.obtenerEstadoAceptaIntercambio(1);

        assertNotNull(dto);
        assertTrue(dto.getAcepta_intercambio());
    }

    @Test
    @DisplayName("HU10 - CP05: Lanza excepción si el producto no existe")
    void testProductoNoExiste() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.obtenerStockProductoPorId(99));
        assertThrows(ResourceNotFoundException.class, () -> productService.obtenerFechaExpiracion(99));
        assertThrows(ResourceNotFoundException.class, () -> productService.obtenerEstado(99));
        assertThrows(ResourceNotFoundException.class, () -> productService.obtenerEstadoAceptaIntercambio(99));
    }

    @Test
    @DisplayName("HU10 - CP06: Lanza excepción si el stock es negativo")
    void testStockNegativo() {
        mockProduct.setCantidad_disponible(-3);
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));

        assertThrows(BadRequestException.class, () -> productService.obtenerStockProductoPorId(1));
    }
    @Test
    @DisplayName("HU10 - CP07: Lanza excepción si la fecha de expiración ya pasó")
    void testFechaExpirada() {
        mockProduct.setFecha_expiracion(LocalDate.now().minusDays(1));
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));

        assertThrows(BadRequestException.class, () -> productService.obtenerFechaExpiracion(1));
    }

}
