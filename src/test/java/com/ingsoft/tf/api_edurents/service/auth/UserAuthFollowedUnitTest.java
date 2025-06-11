package com.ingsoft.tf.api_edurents.service.auth;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.FollowedProductRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthFollowedProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserAuthFollowedUnitTest {

    @Mock
    private FollowedProductRepository followedRepo;
    @Mock private ProductRepository productRepo;
    @Mock private UserRepository userRepo;

    @InjectMocks
    private UserAuthFollowedProductServiceImpl service;

    private User mockUser;
    private Product mockProduct;
    private FollowedProduct followedProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User(); mockUser.setId(1);
        mockProduct = new Product(); mockProduct.setId(1);
        followedProduct = new FollowedProduct();
        followedProduct.setUsuario(mockUser);
        followedProduct.setProducto(mockProduct);
    }

    // Endpoint 05
    @Test
    @DisplayName("HU12 - CP05: Seguir producto nuevo")
    void testFollowProduct() {
        when(userRepo.findById(1)).thenReturn(Optional.of(mockUser));
        when(productRepo.findById(1)).thenReturn(Optional.of(mockProduct));
        when(followedRepo.existsByUsuarioIdAndProductoId(1, 1)).thenReturn(false);

        service.followProduct(1, 1);
        verify(followedRepo).save(any(FollowedProduct.class));
    }

    // Endpoint 06
    @Test
    @DisplayName("HU12 - CP06: Dejar de seguir producto")
    void testUnfollowProduct() {
        when(followedRepo.findByUsuarioIdAndProductoId(1, 1)).thenReturn(Optional.of(followedProduct));

        service.unfollowProduct(1, 1);
        verify(followedRepo).delete(followedProduct);
    }

    @Test
    @DisplayName("HU12 - CP07: Obtener productos seguidos por usuario")
    void testGetFollowedProductsByUser() {
        when(followedRepo.findByUsuarioId(1)).thenReturn(List.of(followedProduct));
        mockProduct.setNombre("Libro"); mockProduct.setDescripcion("Texto de física");
        mockProduct.setEstado(null); // Puedes poner un enum válido si aplica

        List<ShowProductDTO> lista = service.getFollowedProductsByUser(1);
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Libro", lista.get(0).getNombre());
    }
    @Test
    @DisplayName("HU12 - CP10: Validar que la fecha de seguimiento no sea nula")
    void testFechaDeSeguimientoNoNula() {
        when(userRepo.findById(1)).thenReturn(Optional.of(mockUser));
        when(productRepo.findById(1)).thenReturn(Optional.of(mockProduct));
        when(followedRepo.existsByUsuarioIdAndProductoId(1, 1)).thenReturn(false);

        // Mock para simular que la fecha se asigna correctamente
        doAnswer(invocation -> {
            FollowedProduct fp = invocation.getArgument(0);
            assertNotNull(fp.getFecha_inicio_seguimiento());
            return null;
        }).when(followedRepo).save(any());

        service.followProduct(1, 1);
    }
}
