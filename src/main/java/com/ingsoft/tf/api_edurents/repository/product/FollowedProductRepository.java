package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowedProductRepository extends JpaRepository<FollowedProduct, Integer> {
    // Verificar si ya sigue un producto
    @Query("SELECT fp FROM FollowedProduct fp WHERE fp.usuario.id = :idUser AND fp.producto.id = :idProduct")
    Optional<FollowedProduct> findByUsuarioAndProducto(Integer idUser, Integer idProduct);

    // Eliminar seguimiento por IDs
    void deleteByUsuarioIdAndProductoId(Integer idUser, Integer idProduct);

    boolean existsByUsuarioIdAndProductoId(Integer idUsuario, Integer idProducto);
    Optional<FollowedProduct> findByUsuarioIdAndProductoId(Integer idUsuario, Integer idProducto);

    List<FollowedProduct> findByUsuarioId(Integer idUsuario);
}
