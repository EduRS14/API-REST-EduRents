package com.ingsoft.tf.api_edurents.service.auth.user;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ExchangeOfferMapper;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.user.UserAuthExchangeOfferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserAuthExchangeOfferServiceUnitTest {

    @Mock
    private ExchangeOfferRepository exchangeOfferRepository;

    @Mock
    private ExchangeOfferMapper exchangeOfferMapper;

    @InjectMocks
    private UserAuthExchangeOfferServiceImpl userAuthExchangeOfferService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("HU2 - CP01 - Crear intercambio con datos válidos")
    public void crearIntercambio_validData_returnsCreated() {
        ExchangeOfferDTO exchangeOfferDTO = new ExchangeOfferDTO();
        exchangeOfferDTO.setId_usuario(1);
        exchangeOfferDTO.setId_producto(1);
        exchangeOfferDTO.setMensaje_propuesta("Propuesta de intercambio");

        User user = new User();
        user.setId(1);
        user.setNombres("testuser");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setNombres("testuser");

        Product product = new Product();
        product.setId(1);
        product.setNombre("testproduct");

        ShowProductDTO showProductDTO = new ShowProductDTO();
        showProductDTO.setId(1);
        showProductDTO.setNombre("testproduct");

        ExchangeOffer exchangeOfferAux = new ExchangeOffer();

        ExchangeOffer exchangeOffer = new ExchangeOffer();
        exchangeOffer.setId(1);
        exchangeOffer.setProducto(product);
        exchangeOffer.setUsuario(user);
        exchangeOffer.setMensaje_propuesta("Propuesta de intercambio");
        exchangeOffer.setEstado(ExchangeStatus.PENDIENTE);

        ShowExchangeOfferDTO showExchangeOfferDTO = new ShowExchangeOfferDTO();
        showExchangeOfferDTO.setId(1);
        showExchangeOfferDTO.setUsuario(userDTO);
        showExchangeOfferDTO.setProducto(showProductDTO);
        showExchangeOfferDTO.setMensaje_propuesta("Propuesta de intercambio");
        showExchangeOfferDTO.setEstado(ExchangeStatus.PENDIENTE);

        when(exchangeOfferMapper.toEntity(exchangeOfferAux, exchangeOfferDTO,  "crear"))
                .thenReturn(exchangeOffer);
        when(exchangeOfferRepository.save(exchangeOffer)).thenReturn(exchangeOffer);
        when(exchangeOfferMapper.toResponse(exchangeOffer)).thenReturn(showExchangeOfferDTO);

        ShowExchangeOfferDTO result = userAuthExchangeOfferService.crearIntercambio(exchangeOfferDTO);

        assertNotNull(result);
        assertEquals(ExchangeStatus.PENDIENTE, result.getEstado());

    }

    @Test
    @DisplayName("HU2 - CP02 - Crear intercambio sin usuario para la ID dada")
    public void crearIntercambio_noUserForId_throwsException() {
        ExchangeOfferDTO exchangeOfferDTO = new ExchangeOfferDTO();
        exchangeOfferDTO.setId_usuario(999);
        exchangeOfferDTO.setId_producto(1);
        exchangeOfferDTO.setMensaje_propuesta("Propuesta de intercambio");

        ExchangeOffer exchangeOfferAux = new ExchangeOffer();

        when(exchangeOfferMapper.toEntity(exchangeOfferAux, exchangeOfferDTO, "crear"))
                .thenThrow(new ResourceNotFoundException("Usuario no encontrado con ID: " + exchangeOfferDTO.getId_usuario()));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthExchangeOfferService.crearIntercambio(exchangeOfferDTO);
        });

    }

    @Test
    @DisplayName("HU2 - CP03 - Crear intercambio sin producto para la ID dada")
    public void crearIntercambio_noProductForId_throwsException() {
        ExchangeOfferDTO exchangeOfferDTO = new ExchangeOfferDTO();
        exchangeOfferDTO.setId_usuario(1);
        exchangeOfferDTO.setId_producto(999);
        exchangeOfferDTO.setMensaje_propuesta("Propuesta de intercambio");

        ExchangeOffer exchangeOfferAux = new ExchangeOffer();

        when(exchangeOfferMapper.toEntity(exchangeOfferAux, exchangeOfferDTO, "crear"))
                .thenThrow(new ResourceNotFoundException("Producto no encontrado con ID: " + exchangeOfferDTO.getId_producto()));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthExchangeOfferService.crearIntercambio(exchangeOfferDTO);
        });
    }

    @Test
    @DisplayName("HU2 - CP04 - Obtener intercambios por usuario con ID válida")
    public void obtenerIntercambiosPorUsuario_validId_returnsExchangeOffers() {

        User user = new User();
        user.setId(1);
        user.setNombres("testuser");

        Product product1 = new Product();
        product1.setId(1);
        product1.setNombre("testproduct");

        Product product2 = new Product();
        product2.setId(2);
        product2.setNombre("anotherproduct");

        ExchangeOffer exchangeOffer1 = new ExchangeOffer();
        exchangeOffer1.setId(1);
        exchangeOffer1.setMensaje_propuesta("Propuesta de intercambio 1");
        exchangeOffer1.setEstado(ExchangeStatus.PENDIENTE);
        exchangeOffer1.setUsuario(user);
        exchangeOffer1.setProducto(product1);

        ExchangeOffer exchangeOffer2 = new ExchangeOffer();
        exchangeOffer2.setId(2);
        exchangeOffer2.setMensaje_propuesta("Propuesta de intercambio 2");
        exchangeOffer2.setEstado(ExchangeStatus.PENDIENTE);
        exchangeOffer2.setUsuario(user);
        exchangeOffer2.setProducto(product2);

        ShowProductDTO showProductDTO1 = new ShowProductDTO();
        showProductDTO1.setId(1);
        showProductDTO1.setNombre("testproduct");

        ShowProductDTO showProductDTO2 = new ShowProductDTO();
        showProductDTO2.setId(2);
        showProductDTO2.setNombre("anotherproduct");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setNombres("testuser");

        ShowExchangeOfferDTO showExchangeOfferDTO1 = new ShowExchangeOfferDTO();
        showExchangeOfferDTO1.setId(1);
        showExchangeOfferDTO1.setUsuario(userDTO);
        showExchangeOfferDTO1.setProducto(showProductDTO1);
        showExchangeOfferDTO1.setMensaje_propuesta("Propuesta de intercambio 1");
        showExchangeOfferDTO1.setEstado(ExchangeStatus.PENDIENTE);

        ShowExchangeOfferDTO showExchangeOfferDTO2 = new ShowExchangeOfferDTO();
        showExchangeOfferDTO2.setId(2);
        showExchangeOfferDTO2.setUsuario(userDTO);
        showExchangeOfferDTO2.setProducto(showProductDTO2);
        showExchangeOfferDTO2.setMensaje_propuesta("Propuesta de intercambio 2");
        showExchangeOfferDTO2.setEstado(ExchangeStatus.PENDIENTE);

        when(exchangeOfferRepository.findAllByUsuarioId(1)).thenReturn(Arrays.asList(exchangeOffer1, exchangeOffer2));
        when(exchangeOfferMapper.toResponse(exchangeOffer1)).thenReturn(showExchangeOfferDTO1);
        when(exchangeOfferMapper.toResponse(exchangeOffer2)).thenReturn(showExchangeOfferDTO2);

        List<ShowExchangeOfferDTO> result = userAuthExchangeOfferService.obtenerIntercambiosPorUsuario(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("HU2 - CP05 - Obtener intercambios por usuario con ID no existente")
    public void obtenerIntercambiosPorUsuario_nonExistentId_throwsException() {
        when(exchangeOfferRepository.findAllByUsuarioId(999))
                .thenThrow(new ResourceNotFoundException("No se encontraron intercambios para el usuario con ID: 999"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthExchangeOfferService.obtenerIntercambiosPorUsuario(999);
        });
    }

    @Test
    @DisplayName("HU2 - CP06 - Actualizar intercambio con ID válido y estado PENDIENTE")
    public void actualizarIntercambio_validIdAndPendingStatus_returnsUpdatedExchangeOffer() {
        ExchangeOfferDTO exchangeOfferDTO = new ExchangeOfferDTO();
        exchangeOfferDTO.setId_producto(2);
        exchangeOfferDTO.setId_usuario(1);
        exchangeOfferDTO.setMensaje_propuesta("Nueva propuesta de intercambio");

        User user = new User();
        user.setId(1);
        user.setNombres("testuser");

        Product product = new Product();
        product.setId(1);
        product.setNombre("testproduct");

        Product product2 = new Product();
        product2.setId(2);
        product2.setNombre("anotherproduct");

        ExchangeOffer existingExchangeOffer = new ExchangeOffer();
        existingExchangeOffer.setId(1);
        existingExchangeOffer.setMensaje_propuesta("Propuesta de intercambio");
        existingExchangeOffer.setEstado(ExchangeStatus.PENDIENTE);
        existingExchangeOffer.setUsuario(user);
        existingExchangeOffer.setProducto(product);

        ExchangeOffer updatedExchangeOffer = new ExchangeOffer();
        updatedExchangeOffer.setId(1);
        updatedExchangeOffer.setMensaje_propuesta("Nueva propuesta de intercambio");
        updatedExchangeOffer.setEstado(ExchangeStatus.ACEPTADO);
        updatedExchangeOffer.setUsuario(user);
        updatedExchangeOffer.setProducto(product2);

        ShowProductDTO showProductDTO = new ShowProductDTO();
        showProductDTO.setId(2);
        showProductDTO.setNombre("anotherproduct");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setNombres("testuser");

        ShowExchangeOfferDTO updatedShowExchangeOfferDTO = new ShowExchangeOfferDTO();
        updatedShowExchangeOfferDTO.setId(1);
        updatedShowExchangeOfferDTO.setUsuario(userDTO);
        updatedShowExchangeOfferDTO.setProducto(showProductDTO);
        updatedShowExchangeOfferDTO.setMensaje_propuesta("Nueva propuesta de intercambio");
        updatedShowExchangeOfferDTO.setEstado(ExchangeStatus.ACEPTADO);

        when(exchangeOfferRepository.findById(1)).thenReturn(Optional.of(existingExchangeOffer));
        when(exchangeOfferMapper.toEntity(existingExchangeOffer, exchangeOfferDTO, "editar"))
                .thenReturn(updatedExchangeOffer);
        when(exchangeOfferRepository.save(updatedExchangeOffer)).thenReturn(updatedExchangeOffer);
        when(exchangeOfferMapper.toResponse(updatedExchangeOffer)).thenReturn(updatedShowExchangeOfferDTO);

        ShowExchangeOfferDTO result = userAuthExchangeOfferService.actualizarIntercambio(1, exchangeOfferDTO);

        assertNotNull(result);
        assertEquals(ExchangeStatus.ACEPTADO, result.getEstado());
    }

    @Test
    @DisplayName("HU2 - CP07 - Actualizar intercambio con ID no existente de intercambio")
    public void actualizarIntercambio_nonExistentExchangeOfferId_throwsException() {
        ExchangeOfferDTO exchangeOfferDTO = new ExchangeOfferDTO();
        exchangeOfferDTO.setId_producto(2);
        exchangeOfferDTO.setId_usuario(999);
        exchangeOfferDTO.setMensaje_propuesta("Nueva propuesta de intercambio");

        when(exchangeOfferRepository.findById(999))
                .thenThrow(new ResourceNotFoundException("Intercambio no encontrado con ID: 999"));

        assertThrows(ResourceNotFoundException.class, () -> {
            userAuthExchangeOfferService.actualizarIntercambio(999, exchangeOfferDTO);
        });
    }

    @Test
    @DisplayName("HU2 - CP08 - Actualizar intercambio con estado diferente a PENDIENTE")
    public void actualizarIntercambio_nonPendingStatus_throwsException() {
        ExchangeOfferDTO exchangeOfferDTO = new ExchangeOfferDTO();
        exchangeOfferDTO.setId_producto(3);
        exchangeOfferDTO.setId_usuario(1);
        exchangeOfferDTO.setMensaje_propuesta("Nueva propuesta de intercambio");

        User user = new User();
        user.setId(1);
        user.setNombres("testuser");

        Product product = new Product();
        product.setId(1);
        product.setNombre("testproduct");

        ExchangeOffer existingExchangeOffer = new ExchangeOffer();
        existingExchangeOffer.setId(1);
        existingExchangeOffer.setMensaje_propuesta("Propuesta de intercambio");
        existingExchangeOffer.setEstado(ExchangeStatus.ACEPTADO);
        existingExchangeOffer.setUsuario(user);
        existingExchangeOffer.setProducto(product);

        when(exchangeOfferRepository.findById(1)).thenReturn(Optional.of(existingExchangeOffer))
                .thenThrow(new BadRequestException("El intercambio con ID 1 no se puede editar porque no está en estado PENDIENTE."));

        assertThrows(BadRequestException.class, () -> {
            userAuthExchangeOfferService.actualizarIntercambio(1, exchangeOfferDTO);
        });
    }


}
