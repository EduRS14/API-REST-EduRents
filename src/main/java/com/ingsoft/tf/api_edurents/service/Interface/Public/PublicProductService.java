package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PublicProductService {

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

    @Transactional(readOnly = true)
    StockDTO obtenerStockProductoPorId(Integer idProducto);

    @Transactional
    ShowProductDTO actualizarCantidadDisponible(Integer idProducto, Integer nuevaCantidad);

    ShowProductDTO actualizarFechaExpiracion(Integer id, LocalDate fechaExpiracion);

    ShowProductDTO obtenerFechaExpiracion(Integer id);

    ShowProductDTO obtenerEstado(Integer id);

    ShowProductDTO obtenerEstadoAceptaIntercambio(Integer id);

    @Transactional(readOnly = true)
    List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor);
}
