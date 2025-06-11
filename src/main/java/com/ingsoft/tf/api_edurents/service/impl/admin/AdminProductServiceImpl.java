package com.ingsoft.tf.api_edurents.service.impl.admin;

import com.ingsoft.tf.api_edurents.dto.product.*;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.*;
import com.ingsoft.tf.api_edurents.repository.product.*;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    // Agregamos el mapper de Product
    @Autowired
    private ProductMapper productMapper;

    // PENDIENTE

    private StockDTO convertToProductStockDTO(Product producto) {
        StockDTO dto = new StockDTO();
        dto.setId(producto.getId());
        dto.setCantidad_disponible(producto.getCantidad_disponible());
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public StockDTO obtenerStockProductoPorId(Integer idProducto) {
        Product producto = productRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));
        return convertToProductStockDTO(producto);
    }
    
    @Transactional
    @Override
    public ShowProductDTO actualizarCantidadDisponible(Integer idProducto, Integer nuevaCantidad) {
        if (nuevaCantidad < 0) {
            throw new BadRequestException("La cantidad no puede ser negativa");
        }
        Product producto = productRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        producto.setCantidad_disponible(nuevaCantidad);
        productRepository.save(producto);
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);
        return productoDTOMostrar;
    }

    public ShowProductDTO actualizarFechaExpiracion(Integer id, LocalDate fechaExpiracion) {
        // Obtener el producto por ID
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Actualizar la fecha de expiración
        producto.setFecha_expiracion(fechaExpiracion);

        // Guardar el producto actualizado
        productRepository.save(producto);

        // Crear y devolver el DTO con la nueva fecha de expiración
        ShowProductDTO dto = new ShowProductDTO();
        dto.setFecha_expiracion(producto.getFecha_expiracion());

        return dto;
    }

    public ShowProductDTO obtenerFechaExpiracion(Integer id) {
        // Obtener el producto de la base de datos
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Verifica si la fecha de expiración es null en la entidad
        System.out.println("Fecha Expiración en el Producto: " + producto.getFecha_expiracion());

        // Crear un DTO para devolver solo la información necesaria
        ShowProductDTO dto = new ShowProductDTO();
        dto.setFecha_expiracion(producto.getFecha_expiracion());

        return dto;
    }



    // Extra?

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor) {
        List<Product> productos = productRepository.findByVendedor(idVendedor);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el vendedor con id: " + idVendedor);
        }
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }












}
