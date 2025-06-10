package com.ingsoft.tf.api_edurents.dto.product;

import lombok.Data;

@Data
public class FollowedProductDTO {
    private Integer idProducto;
    private String nombreProducto;
    private String descripcion;
    private String categoria;
    private String estado;
}
