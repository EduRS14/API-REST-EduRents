package com.ingsoft.tf.api_edurents.service.Interface.auth.seller;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;

public interface SellerAuthProductService {

    ShowProductDTO crearProducto(ProductDTO productoDTO);
    ShowProductDTO editarProducto(Integer id, ProductDTO productoDTO);
    void eliminarProducto(Integer id);

}
