package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;

import java.util.List;

public interface UserAuthFollowedProductService {
    void followProduct(Integer idUser, Integer idProduct);

    void unfollowProduct(Integer idUser, Integer idProduct);

    List<ShowProductDTO> getFollowedProductsByUser(Integer idUser);
}
