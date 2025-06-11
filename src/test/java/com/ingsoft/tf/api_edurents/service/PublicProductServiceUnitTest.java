package com.ingsoft.tf.api_edurents.service;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
