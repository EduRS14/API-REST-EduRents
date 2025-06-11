package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicProductServiceImpl implements PublicProductService {

    @Autowired
    private ProductRepository productRepository;

    // Agregamos el mapper de Product
    @Autowired
    private ProductMapper productMapper;

    // HU 01

    @Transactional(readOnly = true)
    @Override
    public ShowProductDTO obtenerProductoPorId(Integer id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return productMapper.toResponse(producto);
    }

    // HU 03

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerTodosLosProductos() {
        List<Product> productos = productRepository.findAll();
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    // HU 04

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarrera(Integer idCarrera) {
        List<Product> productos = productRepository.findByCareer(idCarrera);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException(("No se encontraron productos para la carrera con id: " + idCarrera));
        }
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerProductosPorCurso(Integer idCurso) {
        List<Product> productos = productRepository.findByCourse(idCurso);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el curso con id: " + idCurso);
        }
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCursoYCarrera(Integer idCarrera, Integer idCurso) {
        List<Product> productos = productRepository.findByCareerAndCourse(idCarrera, idCurso);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCarrera + " y curso con id: " + idCurso);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCategoria(Integer idCategoria) {
        List<Product> productos = productRepository.findByCategoryId(idCategoria);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la categoría con id: " + idCategoria);
        }
        return productos.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraCursoYCategoria(Integer idCarrera, Integer idCurso, Integer idCategoria) {
        List<Product> productos = productRepository.findByCareerAndCourseAndCategorias(idCarrera, idCurso, idCategoria);
        if(productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCarrera + ",curso con id: " + idCurso + "y categoria con id: " + idCategoria);
        }
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    // HU05

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraOrdenarPorVistas(Integer idCareer){
        List<Product> products = productRepository.findByCareerOrderByViews(idCareer);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCareer);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCursoOrdenarPorVistas(Integer idCourse){
        List<Product> products = productRepository.findByCourseOrderByViews(idCourse);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el curso con id: " + idCourse);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraPorCursoOrdenarPorVistas(Integer idCareer, Integer idCourse){
        List<Product> products = productRepository.findByCourseCareerOrderByViews(idCareer, idCourse);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carera con id: " + idCareer + " y curso con id: " + idCourse);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCategoriaOrdernarPorVistas(Integer idCategory){
        List<Product> products = productRepository.findByCategoryOrderByViews(idCategory);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la categoria con id: " + idCategory);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(Integer idCareer, Integer idCourse, Integer idCategory){
        List<Product> products = productRepository.findByCareerCourseCategoryOrderByViews(idCareer, idCourse, idCategory);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para la carrera con id: " + idCareer + " , curso con id: " + idCourse + " y categoria con id: " + idCategory);
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    // HU06

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerTop10ProductosPorVistas() {
        Pageable top10 = PageRequest.of(0, 10);
        List<Product> products = productRepository.findAllByOrderByVistasDesc(top10);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el top 10 en base a vistas");
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtenerTop10ProductosPorCantidadDeIntercambios() {
        Pageable top10 = PageRequest.of(0, 10);
        List<Product> products = productRepository.findTopProductsByExchangeOfferCount(top10);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos para el top 10 en base a cantidad de intercambios");
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<ShowProductDTO> obtener10ProductosRecientes(){
        Pageable top10 = PageRequest.of(0, 10);
        List<Product> products = productRepository.findAllByOrderByFecha_creacionDesc(top10);
        if(products.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos recientes");
        }
        return products.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }



}
