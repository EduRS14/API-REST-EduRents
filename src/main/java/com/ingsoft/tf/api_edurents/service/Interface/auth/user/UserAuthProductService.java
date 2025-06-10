package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;

import java.util.List;

public interface UserAuthProductService {

    // HU 03
    List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor);
    List<ShowProductDTO> obtenerProductosPorVendedorYCurso(Integer idVendedor, Integer idCurso);
    List<ShowProductDTO> obtenerProductosPorVendedorYCarrera(Integer idVendedor, Integer idCarrera);
    List<ShowProductDTO> obtenerProductosPorVendedorYCategoria(Integer idVendedor, Integer idCategoria);

    // HU 05
    void sumarView(Integer id);
    List<ShowProductDTO> obtenerProductosPorIdVendedorOrdenarPorVistas(Integer idSeller);
    List<ShowProductDTO> obtenerProductosRecomendados(Integer idCareer);
}
