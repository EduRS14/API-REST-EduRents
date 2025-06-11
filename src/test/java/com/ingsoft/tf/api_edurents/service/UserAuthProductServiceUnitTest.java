package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthProductService;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAuthProductServiceUnitTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private UserAuthProductServiceImpl userAuthProductService;

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
