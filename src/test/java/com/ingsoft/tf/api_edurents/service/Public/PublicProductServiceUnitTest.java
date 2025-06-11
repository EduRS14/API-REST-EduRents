package com.ingsoft.tf.api_edurents.service.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    // Los casos de prueba restantes de la HU3 se encuentran en UserAuthProductServiceUnitTest

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

}
