package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  
    @Query("SELECT p FROM Product p JOIN p.categorias cp WHERE cp.categoria.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Integer categoryId);
    //Puedes omitir el Param, spring lo va a mapear si setá con el mismo nombre

    @Query("""
SELECT DISTINCT p FROM Product p
JOIN p.cursos_carreras ccp
WHERE ccp.curso_carrera.curso.id = :courseId
  AND ccp.curso_carrera.carrera.id = :careerId
""")
    List<Product> findByCareerAndCourse(
            @Param("careerId") Integer careerId,
            @Param("courseId") Integer courseId
    );

    @Query("SELECT p FROM Product p WHERE p.vendedor.id = :id_vendedor")
    List<Product> findByVendedor(@Param("id_vendedor") Integer id_vendedor);

    @Query("SELECT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.curso.id = :idCurso AND p.vendedor.id = :idVendedor")
    List<Product> findByVendedorAndCourse(@Param("idVendedor") Integer idVendedor, @Param("idCurso") Integer idCurso);

    @Query("SELECT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.carrera.id = :idCarrera AND p.vendedor.id = :idVendedor")
    List<Product> findByVendedorAndCareer(@Param("idVendedor") Integer idVendedor, @Param("idCarrera") Integer idCarrera);

    @Query("SELECT p FROM Product p JOIN p.categorias cp WHERE cp.categoria.id = :idCategoria AND p.vendedor.id = :idVendedor")
    List<Product> findByVendedorAndCategory(@Param("idVendedor") Integer idVendedor, @Param("idCategoria") Integer idCategoria);

    // HU 04

    @Query("SELECT DISTINCT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.carrera.id = :id_career")
    List<Product> findByCareer(@Param("career") Integer id_career);

    @Query("SELECT DISTINCT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.curso.id = :id_course")
    List<Product> findByCourse(@Param("id_course") Integer id_course);

    @Query("""
SELECT DISTINCT p FROM Product p
JOIN p.categorias cp
JOIN p.cursos_carreras ccp
WHERE ccp.curso_carrera.curso.id = :id_course
  AND ccp.curso_carrera.carrera.id = :id_career
  AND cp.categoria.id = :id_category
""")
    List<Product> findByCareerAndCourseAndCategorias(
            @Param("id_career") Integer id_career,
            @Param("id_course") Integer id_course,
            @Param("id_category") Integer id_category
    );

    /* eXTRA?

    @Query("SELECT p FROM Product p WHERE p.vendedor.id = :id_vendedor")
    List<Product> findByVendedor(@Param("id_vendedor") Integer id_vendedor);

    */

    // HU 05

    @Query("SELECT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.carrera.id = : idCareer ORDER BY p.vistas DESC")
    List<Product> findByCareerOrderByViews(@Param("idCareer") Integer idCareer);

    @Query("SELECT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.curso.id = : idCourse ORDER BY p.vistas DESC")
    List<Product> findByCourseOrderByViews(@Param("idCourse") Integer idCourse);

    @Query("SELECT p FROM Product p JOIN p.cursos_carreras ccp WHERE ccp.curso_carrera.carrera.id = : idCareer AND ccp.curso_carrera.curso.id = : idCourse ORDER BY p.vistas DESC")
    List<Product> findByCourseCareerOrderByViews(@Param("idCareer") Integer idCareer, @Param("idCourse") Integer idCourse);

    @Query("SELECT p FROM Product p JOIN p.categorias cp WHERE cp.categoria.id = : idCategory ORDER BY p.vistas DESC")
    List<Product> findByCategoryOrderByViews(@Param("idCategory") Integer idCategory);

    @Query("""
SELECT p FROM Product p
JOIN p.categorias cp
JOIN p.cursos_carreras ccp
WHERE ccp.curso_carrera.curso.id = :id_course
  AND ccp.curso_carrera.carrera.id = :id_career
  AND cp.categoria.id = :id_category
ORDER BY p.vistas DESC
""")
    List<Product> findByCareerCourseCategoryOrderByViews(
            @Param("id_career") Integer id_career,
            @Param("id_course") Integer id_course,
            @Param("id_category") Integer id_category
    );

    @Query("SELECT p FROM Product p WHERE p.vendedor.id = : idSeller ORDER BY p.vistas")
    List<Product> findBySellerIdOrderByViews(@Param("idSeller") Integer idSeller);

    // HU06

    List<Product> findAllByOrderByVistasDesc(Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN p.intercambios i GROUP BY p ORDER BY COUNT(i) DESC")
    List<Product> findTopProductsByExchangeOfferCount(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.fecha_creacion DESC ")
    List<Product> findAllByOrderByFecha_creacionDesc(Pageable pageable);



}
