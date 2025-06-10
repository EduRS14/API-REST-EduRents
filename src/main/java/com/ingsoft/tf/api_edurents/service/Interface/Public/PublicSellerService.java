package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerReputationDTO;

import java.util.List;

public interface PublicSellerService {
    SellerDTO getPublicSellerProfile(Integer idSeller);

    // Endpoint 04: GET /sellers/name/{name}
    SellerDTO getSellerByNombreUsuario(String nombreUsuario);

    // Endpoint 05: GET /sellers/{idSeller}/reputation
    SellerReputationDTO getSellerReputation(Integer idSeller);

    // Endpoint 06: GET /sellers/{idSeller}/products
    List<ShowProductDTO> getProductsBySeller(Integer idSeller);
}
