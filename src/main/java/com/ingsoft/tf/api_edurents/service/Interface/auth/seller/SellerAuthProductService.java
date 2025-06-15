package com.ingsoft.tf.api_edurents.service.Interface.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;

import java.time.LocalDate;

public interface SellerAuthProductService {

    // HU1
    ShowProductDTO crearProducto(ProductDTO productoDTO);
    ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO);
    void eliminarProducto(Integer id);

    // HU10
    ShowProductDTO actualizarCantidadDisponible(Integer idProducto, Integer nuevaCantidad);
    ShowProductDTO actualizarFechaExpiracion(Integer id, LocalDate fechaExpiracion);

}
