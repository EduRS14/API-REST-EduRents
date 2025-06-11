package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.product.*;
import com.ingsoft.tf.api_edurents.model.entity.university.CoursesCareers;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.*;
import com.ingsoft.tf.api_edurents.repository.university.CoursesCareersRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final SellerMapper sellerMapper;
    private final ImageMapper imageMapper;
    private final CourseCareerProductMapper coursesCareersProductMapper;
    private final CategoriesProductsMapper categoriesProductsMapper;
    private final SellerRepository sellerRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final CategoriesProductsRepository categoriesProductsRepository;
    private final CoursesCareersRepository coursesCareersRepository;
    private final CoursesCareersProductRepository coursesCareersProductRepository;
    private final ProductRepository productRepository;

    public ProductMapper(
            CategoryMapper categoryMapper,
            SellerMapper sellerMapper,
            ImageMapper imageMapper,
            CourseCareerProductMapper coursesCareersProductMapper,
            CategoriesProductsMapper categoriesProductsMapper,
            SellerRepository sellerRepository, ImageRepository imageRepository, CategoryRepository categoryRepository, CategoriesProductsRepository categoriesProductsRepository, CoursesCareersRepository coursesCareersRepository, CoursesCareersProductRepository coursesCareersProductRepository, ProductRepository productRepository) {
        this.categoryMapper = categoryMapper;
        this.sellerMapper = sellerMapper;
        this.imageMapper = imageMapper;
        this.coursesCareersProductMapper = coursesCareersProductMapper;
        this.categoriesProductsMapper = categoriesProductsMapper;
        this.sellerRepository = sellerRepository;
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.categoriesProductsRepository = categoriesProductsRepository;
        this.coursesCareersRepository = coursesCareersRepository;
        this.coursesCareersProductRepository = coursesCareersProductRepository;
        this.productRepository = productRepository;
    }

    public Product toEntity(Product base, ProductDTO request, String tipoRequest) {
        base.setNombre(request.getNombre());
        base.setDescripcion(request.getDescripcion());
        base.setPrecio(request.getPrecio());
        base.setEstado(request.getEstado());
        base.setCantidad_disponible(request.getCantidad_disponible());
        base.setAcepta_intercambio(request.getAcepta_intercambio());
        base.setFecha_creacion(LocalDate.now());
        base.setFecha_expiracion(request.getFecha_expiracion());

        if (tipoRequest.equals("editar")) {
            base.setFecha_modificacion(LocalDate.now());
        }

        Seller vendedor = sellerRepository.findById(request.getId_vendedor())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado"));
        base.setVendedor(vendedor);

        productRepository.save(base);

        // Imagenes
        base.getImagenes().clear();
        if (request.getUrls_imagenes() != null) {
            List<Image> imagenes = request.getUrls_imagenes().stream()
                    .map(url -> {
                        Image imagen = new Image();
                        imagen.setUrl(url);
                        imagen.setProducto(base);
                        imageRepository.save(imagen);
                        return imagen;
                    }).toList();
            base.getImagenes().addAll(imagenes);
        }

        // Categorias
        base.getCategorias().clear();
        if (request.getCategorias() != null) {
            List<CategoriesProducts> categorias = request.getCategorias().stream()
                    .map(catId -> {
                        Category categoria = categoryRepository.findById(catId)
                                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + catId));
                        CategoriesProducts rel = new CategoriesProducts();
                        rel.setProducto(base);
                        rel.setCategoria(categoria);
                        categoriesProductsRepository.save(rel);
                        return rel;
                    }).toList();
            base.getCategorias().addAll(categorias);
        }

        // Cursos y Carreras
        base.getCursos_carreras().clear();
        if (request.getCursos_carreras() != null) {
            List<CoursesCareersProduct> cursosCarreras = request.getCursos_carreras().stream()
                    .map(id -> {
                        CoursesCareers cursoCarrera = coursesCareersRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Curso/Carrera no encontrado con id: " + id));
                        CoursesCareersProduct rel = new CoursesCareersProduct();
                        rel.setProducto(base);
                        rel.setCurso_carrera(cursoCarrera);
                        coursesCareersProductRepository.save(rel);
                        return rel;
                    }).toList();
            base.getCursos_carreras().addAll(cursosCarreras);
        }

        return base;
    }

    public ShowProductDTO toResponse(Product product) {
        ShowProductDTO dto = new ShowProductDTO();

        dto.setId(product.getId());
        dto.setNombre(product.getNombre());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        dto.setEstado(product.getEstado());
        dto.setCantidad_disponible(product.getCantidad_disponible());
        dto.setAcepta_intercambio(product.getAcepta_intercambio());
        dto.setFecha_creacion(product.getFecha_creacion());
        dto.setFecha_modificacion(product.getFecha_modificacion());
        dto.setFecha_expiracion(product.getFecha_expiracion());
        dto.setVistas(product.getVistas());

        // Vendedor
        dto.setVendedor(sellerMapper.toSellerDTO(product.getVendedor()));

        // Imagenes
        dto.setImagenes(product.getImagenes().stream()
                .map(imageMapper::toImageDTO)
                .toList());

        // Categorias
        dto.setCategorias(product.getCategorias().stream()
                .map(categoriesProductsMapper::toCategoryDTO)
                .toList());

        // CursosCarreras
        dto.setCursos_carreras(product.getCursos_carreras().stream()
                .map(coursesCareersProductMapper::toResponse)
                .toList());

        return dto;
    }

}
