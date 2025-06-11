package com.ingsoft.tf.api_edurents.service.impl.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.seller.SellerAuthProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerAuthProductServiceImpl implements SellerAuthProductService {

    @Autowired
    private ProductRepository productRepository;

    // Agregamos el mapper de Product
    @Autowired
    private ProductMapper productMapper;

    // HU01

    @Transactional
    @Override
    public ShowProductDTO crearProducto(ProductDTO productoDTO) {

        Product producto = new Product();
        // Convertimos el DTO a entidad
        producto = productMapper.toEntity(producto, productoDTO, "crear");

        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);
        return productoDTOMostrar;
    }

    @Transactional
    @Override
    public ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        // Convertimos el DTO a entidad
        producto = productMapper.toEntity(producto, productoDTO, "editar");
        // Convertimos a DTO para devolver
        ShowProductDTO productoDTOMostrar = productMapper.toResponse(producto);
        return productoDTOMostrar;
    }

    @Transactional
    @Override
    public void eliminarProducto(Integer id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        productRepository.delete(producto);
    }

}
