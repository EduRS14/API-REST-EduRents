package com.ingsoft.tf.api_edurents.service.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.impl.auth.seller.SellerAuthProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SellerAuthProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private SellerAuthProductServiceImpl sellerAuthProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("HU1 - CP01 - Crear producto con datos válidos")
    void crearProducto_validData_returnsCreated() {
        ProductDTO productoDTO = new ProductDTO();
        productoDTO.setId_vendedor(1);
        productoDTO.setNombre("Producto de prueba");
        productoDTO.setDescripcion("Descripción del producto de prueba");
        productoDTO.setPrecio(100.0);
        productoDTO.setEstado(ProductStatus.NUEVO);
        productoDTO.setCantidad_disponible(10);
        productoDTO.setAcepta_intercambio(true);

        Product productoPrueba = new Product();

        Product producto = new Product();
        producto.setId(1);
        producto.setNombre("Producto de prueba");
        producto.setDescripcion("Descripción del producto de prueba");
        producto.setPrecio(100.0);
        producto.setEstado(ProductStatus.NUEVO);
        producto.setCantidad_disponible(10);
        producto.setAcepta_intercambio(true);

        Seller vendedor = new Seller();
        vendedor.setId(1);
        producto.setVendedor(vendedor);

        ShowProductDTO productoDTOMostrar = new ShowProductDTO();
        productoDTOMostrar.setId(1);
        productoDTOMostrar.setNombre("Producto de prueba");
        productoDTOMostrar.setDescripcion("Descripción del producto de prueba");
        productoDTOMostrar.setPrecio(100.0);
        productoDTOMostrar.setEstado(ProductStatus.NUEVO);
        productoDTOMostrar.setCantidad_disponible(10);
        productoDTOMostrar.setAcepta_intercambio(true);

        SellerDTO vendedorDTO = new SellerDTO();
        vendedorDTO.setId(1);
        productoDTOMostrar.setVendedor(vendedorDTO);

        // Configurar el comportamiento de los mocks
        when(productMapper.toEntity(productoPrueba, productoDTO, "crear")).thenReturn(producto);
        when(productRepository.save(producto)).thenReturn(producto);
        when(productMapper.toResponse(producto)).thenReturn(productoDTOMostrar);

        ShowProductDTO result = sellerAuthProductService.crearProducto(productoDTO);

        assertNotNull(result);
        assertEquals("Producto de prueba", result.getNombre());

    }

    @Test
    @DisplayName("HU1 - CP02 - Crear producto sin encontrar un vendedor con el ID proporcionado")
    void crearProducto_withoutSeller_throwsException() {
        ProductDTO productoDTO = new ProductDTO();
        productoDTO.setNombre("Producto de prueba");
        productoDTO.setDescripcion("Descripción del producto de prueba");
        productoDTO.setPrecio(100.0);
        productoDTO.setEstado(ProductStatus.NUEVO);
        productoDTO.setCantidad_disponible(10);
        productoDTO.setAcepta_intercambio(true);
        productoDTO.setId_vendedor(999);

        Product productoPrueba = new Product();

        when(productMapper.toEntity(productoPrueba, productoDTO, "crear"))
                .thenThrow(new ResourceNotFoundException("Vendedor no encontrado"));


        assertThrows(ResourceNotFoundException.class, () -> {
            sellerAuthProductService.crearProducto(productoDTO);
        });
    }

    @Test
    @DisplayName("HU1 - CP03 - Crear producto con un estado inválido")
    void crearProducto_invalidStatus_throwsException() {
        ProductDTO productoDTO = new ProductDTO();
        productoDTO.setId_vendedor(1);
        productoDTO.setNombre("Producto de prueba");
        productoDTO.setDescripcion("Descripción del producto de prueba");
        productoDTO.setPrecio(100.0);
        productoDTO.setEstado(null);
        productoDTO.setCantidad_disponible(10);
        productoDTO.setAcepta_intercambio(true);

        Product productoPrueba = new Product();

        when(productMapper.toEntity(productoPrueba, productoDTO, "crear"))
                .thenThrow(new BadRequestException("El estado del producto no puede ser nulo"));

        assertThrows(BadRequestException.class, () -> {
            sellerAuthProductService.crearProducto(productoDTO);
        });
    }

    @Test
    @DisplayName("HU1 - CP04 - Editar producto con datos válidos")
    void editarProducto_validData_returnsUpdated() {
        ProductDTO productoDTO = new ProductDTO();
        productoDTO.setNombre("Producto actualizado");
        productoDTO.setDescripcion("Descripción del producto actualizado");
        productoDTO.setPrecio(150.0);
        productoDTO.setEstado(ProductStatus.USADO);
        productoDTO.setCantidad_disponible(5);
        productoDTO.setAcepta_intercambio(false);

        Product productoExistente = new Product();
        productoExistente.setId(1);
        productoExistente.setNombre("Producto de prueba");
        productoExistente.setDescripcion("Descripción del producto de prueba");
        productoExistente.setPrecio(100.0);
        productoExistente.setEstado(ProductStatus.NUEVO);
        productoExistente.setCantidad_disponible(10);
        productoExistente.setAcepta_intercambio(true);

        ShowProductDTO productoActualizado = new ShowProductDTO();
        productoActualizado.setId(1);
        productoActualizado.setNombre("Producto actualizado");
        productoActualizado.setDescripcion("Descripción del producto actualizado");
        productoActualizado.setPrecio(150.0);
        productoActualizado.setEstado(ProductStatus.USADO);
        productoActualizado.setCantidad_disponible(5);
        productoActualizado.setAcepta_intercambio(false);

        when(productRepository.findById(1)).thenReturn(Optional.of(productoExistente));
        when(productMapper.toEntity(productoExistente, productoDTO, "editar")).thenReturn(productoExistente);
        when(productRepository.save(productoExistente)).thenReturn(productoExistente);
        when(productMapper.toResponse(productoExistente)).thenReturn(productoActualizado);

        ShowProductDTO result = sellerAuthProductService.editarProducto(1, productoDTO);

        assertNotNull(result);
        assertEquals("Producto actualizado", result.getNombre());
    }

    @Test
    @DisplayName("HU1 - CP05 - Editar producto sin encontrar el producto con el ID proporcionado")
    void editarProducto_withoutProduct_throwsException() {
        ProductDTO productoDTO = new ProductDTO();
        productoDTO.setNombre("Producto actualizado");
        productoDTO.setDescripcion("Descripción del producto actualizado");
        productoDTO.setPrecio(150.0);
        productoDTO.setEstado(ProductStatus.USADO);
        productoDTO.setCantidad_disponible(5);
        productoDTO.setAcepta_intercambio(false);

        when(productRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sellerAuthProductService.editarProducto(999, productoDTO);
        });
    }

    @Test
    @DisplayName("HU1 - CP06 - Eliminar producto con ID válido")
    void eliminarProducto_validId_deletesProduct() {
        Product productoExistente = new Product();
        productoExistente.setId(1);
        productoExistente.setNombre("Producto de prueba");
        productoExistente.setDescripcion("Descripción del producto de prueba");
        productoExistente.setPrecio(100.0);
        productoExistente.setEstado(ProductStatus.NUEVO);
        productoExistente.setCantidad_disponible(10);
        productoExistente.setAcepta_intercambio(true);

        when(productRepository.findById(1)).thenReturn(Optional.of(productoExistente));

        sellerAuthProductService.eliminarProducto(1);

        verify(productRepository).delete(productoExistente);
    }

    @Test
    @DisplayName("HU1 - CP07 - Eliminar producto con ID inexistente")
    void eliminarProducto_invalidId_throwsException() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            sellerAuthProductService.eliminarProducto(999);
        });
    }
}
