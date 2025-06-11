package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthProductServiceImpl implements UserAuthProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor) {
        List<Product> productos = productRepository.findByVendedor(idVendedor);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el vendedor con id: " + idVendedor);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorVendedorYCurso(Integer idVendedor, Integer idCurso) {
        List<Product> productos = productRepository.findByVendedorAndCourse(idVendedor, idCurso);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el vendedor con id: " + idVendedor + " y curso con id: " + idCurso);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorVendedorYCarrera(Integer idVendedor, Integer idCarrera) {
        List<Product> productos = productRepository.findByVendedorAndCareer(idVendedor, idCarrera);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el vendedor con id: " + idVendedor + " y carrera con id: " + idCarrera);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorVendedorYCategoria(Integer idVendedor, Integer idCategoria) {
        List<Product> productos = productRepository.findByVendedorAndCategory(idVendedor, idCategoria);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el vendedor con id: " + idVendedor + " y categoría con id: " + idCategoria);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    // HU 05

    @Transactional
    @Override
    public void sumarView(Integer id){
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        producto.setVistas(producto.getVistas() + 1);
        productRepository.save(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorIdVendedorOrdenarPorVistas(Integer idSeller){
        List<Product> products = productRepository.findBySellerIdOrderByViews(idSeller);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos del vendedor con id: " + idSeller);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosRecomendados(Integer idCareer){
        List<Product> products = productRepository.findByCareerOrderByViews(idCareer);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos recomendados para la carrera: " + idCareer);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }


}
