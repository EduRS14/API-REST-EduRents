package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.ProductStatus;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PublicProductService {

    List<ShowProductDTO> obtenerProductoPorNombre(String nombre);

    // HU 01
    ShowProductDTO obtenerProductoPorId(Integer id);

    // HU 03 (Solo 1, los demas en UserAuthProductService)
    List<ShowProductDTO> obtenerTodosLosProductos();

    // HU 04

    List<ShowProductDTO> obtenerProductosPorCarrera(Integer idCarrera);
    List<ShowProductDTO> obtenerProductosPorCurso(Integer idCurso);
    List<ShowProductDTO> obtenerProductosPorCursoYCarrera(Integer idCarrera, Integer idCurso);
    List<ShowProductDTO> obtenerProductosPorCategoria(Integer idCategoria);
    List<ShowProductDTO> obtenerProductosPorCarreraCursoYCategoria(Integer idCarrera, Integer idCurso, Integer idCategoria);

    List<ShowProductDTO> obtenerProductosConFiltros(
            List<Integer> carreras,
            List<Integer> cursos,
            List<Integer> categorias,
            Double precioMin,
            Double precioMax,
            boolean ordenarPorVistas,
            ProductStatus estado
    );

    // HU 05

    List<ShowProductDTO> obtenerProductosPorCarreraOrdenarPorVistas(Integer idCareer);
    List<ShowProductDTO> obtenerProductosPorCursoOrdenarPorVistas(Integer idCourse);
    List<ShowProductDTO> obtenerProductosPorCarreraPorCursoOrdenarPorVistas(Integer idCareer, Integer idCourse);
    List<ShowProductDTO> obtenerProductosPorCategoriaOrdernarPorVistas(Integer idCategory);
    List<ShowProductDTO> obtenerProductosPorCarreraPorCursoPorCategoriaOrdenarPorVistas(Integer idCareer, Integer idCourse, Integer idCategory);

    // HU 06

    List<ShowProductDTO> obtenerTop10ProductosPorVistas();
    List<ShowProductDTO> obtenerTop10ProductosPorCantidadDeIntercambios();
    List<ShowProductDTO> obtener10ProductosRecientes();

    // HU10

    @Transactional(readOnly = true)
    StockDTO obtenerStockProductoPorId(Integer idProducto);



    ShowProductDTO obtenerFechaExpiracion(Integer id);

    ShowProductDTO obtenerEstado(Integer id);

    ShowProductDTO obtenerEstadoAceptaIntercambio(Integer id);

    void aumentarVistas(Integer idProducto);

}
