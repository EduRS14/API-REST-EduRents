package com.ingsoft.tf.api_edurents.service.auth;

import com.ingsoft.tf.api_edurents.dto.product.AlertDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowAlertDTO;
import com.ingsoft.tf.api_edurents.mapper.AlertMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Alert;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.AlertRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthAlertServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class UserAuthAlertUnitTest {
    @Mock
    private AlertRepository alertRepository;
    @Mock private AlertMapper alertMapper;
    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;

    @InjectMocks
    private UserAuthAlertServiceImpl alertService;

    private User mockUser;
    private Product mockProduct;
    private Alert mockAlert;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1);

        mockProduct = new Product();
        mockProduct.setId(1);

        mockAlert = new Alert();
        mockAlert.setId(1);
        mockAlert.setUsuario(mockUser);
        mockAlert.setProducto(mockProduct);
        mockAlert.setFecha_creacion(LocalDateTime.now());
    }

    // Endpoint 01
    @Test
    @DisplayName("HU12 - CP01: Crear alerta válida")
    void testCreateAlert() {
        AlertDTO dto = new AlertDTO();
        dto.setId_usuario(1);
        dto.setId_producto(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(productRepository.findById(1)).thenReturn(Optional.of(mockProduct));
        when(alertMapper.toEntity(any(), any(), any())).thenReturn(mockAlert);
        when(alertRepository.save(any())).thenReturn(mockAlert);
        when(alertMapper.toDTO(any())).thenReturn(dto);

        AlertDTO result = alertService.createAlert(dto);
        assertNotNull(result);
        assertEquals(1, result.getId_usuario());
        assertEquals(1, result.getId_producto());
    }

    // Endpoint 02
    @Test
    @DisplayName("HU12 - CP02: Eliminar alerta por ID")
    void testDeleteAlert() {
        when(alertRepository.existsById(1)).thenReturn(true);
        alertService.deleteAlert(1);
        verify(alertRepository, times(1)).deleteById(1);
    }


    // Endpoint 03
    @Test
    @DisplayName("HU12 - CP03: Obtener alertas ordenadas por fecha")
    void testGetAlertsByUser() {
        when(alertRepository.findByUsuarioId(1)).thenReturn(List.of(mockAlert));
        when(alertMapper.showAlertToDTO(mockAlert)).thenReturn(new ShowAlertDTO());

        List<ShowAlertDTO> alerts = alertService.getAlertsByUser(1);
        assertNotNull(alerts);
        assertEquals(1, alerts.size());
    }

    // Endpoint 04
    @Test
    @DisplayName("HU12 - CP04: Marcar alerta como vista")
    void testMarkAlertAsViewed() {
        when(alertRepository.existsById(1)).thenReturn(true);
        alertService.markAlertAsViewed(1);
        verify(alertRepository, times(1)).markAsViewed(1);
    }

    @Test
    @DisplayName("HU12 - CP08: Lanza excepción si la alerta no existe al eliminar")
    void testEliminarAlertaInexistente() {
        when(alertRepository.existsById(999)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> alertService.deleteAlert(999));
    }

    @Test
    @DisplayName("HU12 - CP09: Lanza excepción si la alerta no existe al marcar como vista")
    void testMarcarAlertaInexistenteComoVista() {
        when(alertRepository.existsById(999)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> alertService.markAlertAsViewed(999));
    }
}
