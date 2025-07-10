package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductoPorNombre(String nombre) {
        List<Product> productos = productRepository.findByNombreContainingIgnoreCase(nombre);
        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos con el nombre: " + nombre);
        }
        return productos.stream()
                .map(producto -> productMapper.toResponse(producto))
                .collect(Collectors.toList());
    }

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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<ShowProductDTO> obtenerProductosConFiltros(
            List<Integer> carreras,
            List<Integer> cursos,
            List<Integer> categorias,
            Double precioMin,
            Double precioMax,
            boolean ordenarPorVistas,
            ProductStatus estado
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        Join<Object, Object> cursosCarrerasJoin = root.join("cursos_carreras", JoinType.LEFT);
        Join<Object, Object> cursoCarreraJoin = cursosCarrerasJoin.join("curso_carrera", JoinType.LEFT);
        Join<Object, Object> categoriaJoin = root.join("categorias", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        if (carreras != null && !carreras.isEmpty()) {
            predicates.add(cursoCarreraJoin.get("carrera").get("id").in(carreras));
        }

        if (cursos != null && !cursos.isEmpty()) {
            predicates.add(cursoCarreraJoin.get("curso").get("id").in(cursos));
        }

        if (categorias != null && !categorias.isEmpty()) {
            predicates.add(categoriaJoin.get("categoria").get("id").in(categorias));
        }

        if (precioMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("precio"), precioMin));
        }

        if (precioMax != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("precio"), precioMax));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estado"), estado));
        }

        cq.select(root).distinct(true).where(cb.and(predicates.toArray(new Predicate[0])));
        if (ordenarPorVistas) {
            cq.orderBy(cb.desc(root.get("vistas")));
        }

        List<Product> productos = entityManager.createQuery(cq).getResultList();

        if (productos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron productos con los filtros proporcionados");
        }


        return productos.stream()
                .map(productMapper::toResponse)
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

    // HU10
    @Transactional(readOnly = true)
    @Override
    public StockDTO obtenerStockProductoPorId(Integer idProducto) {
        Product producto = productRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));

        if (producto.getCantidad_disponible() < 0) {
            throw new BadRequestException("Cantidad de stock inválida (negativa)");
        }

        return productMapper.toStockDTO(producto);
    }


    @Override
    public ShowProductDTO obtenerFechaExpiracion(Integer id) {
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        if (producto.getFecha_expiracion() != null && producto.getFecha_expiracion().isBefore(LocalDate.now())) {
            throw new BadRequestException("La oferta del producto ya expiró");
        }

        ShowProductDTO dto = new ShowProductDTO();
        dto.setFecha_expiracion(producto.getFecha_expiracion());
        return dto;
    }

    @Override
    public ShowProductDTO obtenerEstado(Integer id) {
        // Obtener el producto de la base de datos
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Verifica si la fecha de expiración es null en la entidad
        System.out.println("Estado del producto: " + producto.getEstado());

        // Crear un DTO para devolver solo la información necesaria
        ShowProductDTO dto = new ShowProductDTO();
        dto.setEstado(producto.getEstado());

        return dto;
    }

    @Override
    public ShowProductDTO obtenerEstadoAceptaIntercambio(Integer id) {
        // Obtener el producto de la base de datos
        Product producto = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Verifica si la fecha de expiración es null en la entidad
        System.out.println("Estado de acepta intercambio del producto: " + producto.getAcepta_intercambio());

        // Crear un DTO para devolver solo la información necesaria
        ShowProductDTO dto = new ShowProductDTO();
        dto.setAcepta_intercambio(producto.getAcepta_intercambio());

        return dto;
    }

    @Override
    public void aumentarVistas(Integer idProducto) {
        Product producto = productRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + idProducto));

        producto.setVistas(producto.getVistas() + 1);
        productRepository.save(producto);
    }


}
