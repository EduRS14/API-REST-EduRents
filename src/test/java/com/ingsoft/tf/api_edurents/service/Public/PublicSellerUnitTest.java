package com.ingsoft.tf.api_edurents.service.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerReputationDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.mapper.SellerMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.impl.Public.PublicSellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PublicSellerUnitTest {
    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerMapper sellerMapper;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private PublicSellerServiceImpl sellerService;

    private Seller mockSeller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockSeller = new Seller();
        mockSeller.setId(1);
        mockSeller.setResena("Muy buen trato");
        mockSeller.setConfiabilidad(true);
        mockSeller.setBuena_atencion(true);
        mockSeller.setSin_demoras(true);
    }

    @Test
    @DisplayName("HU11 - CP01: Obtener perfil público de vendedor por ID")
    void testGetPublicSellerProfile() {
        when(sellerRepository.findById(1)).thenReturn(Optional.of(mockSeller));
        when(sellerMapper.toSellerDTO(mockSeller)).thenReturn(new SellerDTO());

        SellerDTO dto = sellerService.getPublicSellerProfile(1);
        assertNotNull(dto);
    }

    @Test
    @DisplayName("HU11 - CP02: Obtener vendedor por nombre de usuario")
    void testGetSellerByNombreUsuario() {
        when(sellerRepository.findByNombreCompletoUsuario("Juan Pérez")).thenReturn(Optional.of(mockSeller));
        when(sellerMapper.toSellerDTO(mockSeller)).thenReturn(new SellerDTO());

        SellerDTO dto = sellerService.getSellerByNombreUsuario("Juan Pérez");
        assertNotNull(dto);
    }

    @Test
    @DisplayName("HU11 - CP03: Obtener reputación del vendedor")
    void testGetSellerReputation() {
        when(sellerRepository.findById(1)).thenReturn(Optional.of(mockSeller));
        when(sellerMapper.toSellerReputationDTO(mockSeller)).thenReturn(new SellerReputationDTO());

        SellerReputationDTO reputation = sellerService.getSellerReputation(1);
        assertNotNull(reputation);
    }

    @Test
    @DisplayName("HU11 - CP04: Obtener productos del vendedor")
    void testGetProductsBySeller() {
        when(sellerRepository.findById(1)).thenReturn(Optional.of(mockSeller));
        when(productRepository.findByVendedor(1)).thenReturn(List.of(new Product()));
        when(productMapper.toResponse(new Product())).thenReturn(new ShowProductDTO());

        List<ShowProductDTO> productos = sellerService.getProductsBySeller(1);
        assertNotNull(productos);
        assertFalse(productos.isEmpty());
    }

    @Test
    @DisplayName("HU11 - CP05: Excepción si vendedor no existe")
    void testVendedorNoExiste() {
        when(sellerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sellerService.getPublicSellerProfile(999));
        assertThrows(RuntimeException.class, () -> sellerService.getSellerReputation(999));
        assertThrows(RuntimeException.class, () -> sellerService.getProductsBySeller(999));
    }
    @Test
    @DisplayName("HU11 - CP10: Lanza excepción si nombre de usuario no existe")
    void testNombreUsuarioNoExiste() {
        when(sellerRepository.findByNombreCompletoUsuario("NoExiste")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sellerService.getSellerByNombreUsuario("NoExiste"));
    }
    @Test
    @DisplayName("HU11 - CP11: Reputación contiene valores válidos")
    void testReputacionConValoresValidos() {
        SellerReputationDTO repDTO = new SellerReputationDTO();
        repDTO.setBuena_atencion(true);
        repDTO.setConfiabilidad(true);
        repDTO.setSin_demoras(true);

        when(sellerRepository.findById(1)).thenReturn(Optional.of(mockSeller));
        when(sellerMapper.toSellerReputationDTO(mockSeller)).thenReturn(repDTO);

        SellerReputationDTO result = sellerService.getSellerReputation(1);
        assertTrue(result.getBuena_atencion());
        assertTrue(result.getConfiabilidad());
        assertTrue(result.getSin_demoras());
    }
    @Test
    @DisplayName("HU11 - CP12: Lista de productos vacía")
    void testListaProductosVacia() {
        when(sellerRepository.findById(1)).thenReturn(Optional.of(mockSeller));
        when(productRepository.findByVendedor(1)).thenReturn(Collections.emptyList());

        List<ShowProductDTO> productos = sellerService.getProductsBySeller(1);
        assertTrue(productos.isEmpty());
    }

}
