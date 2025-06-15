package com.ingsoft.tf.api_edurents.service.Interface.admin;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.StockDTO;

import java.time.LocalDate;
import java.util.List;

public interface AdminProductService {





    // PENDIENTE

    List<ShowProductDTO> obtenerProductosPorVendedor(Integer idVendedor);
    StockDTO obtenerStockProductoPorId(Integer idProducto);
    ShowProductDTO actualizarCantidadDisponible(Integer id, Integer nuevaCantidad);
    ShowProductDTO actualizarFechaExpiracion (Integer id, LocalDate nuevaFecha);
    ShowProductDTO obtenerFechaExpiracion(Integer id);

    // HU 04










}