package com.ingsoft.tf.api_edurents.repository.exchanges;

import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExchangeOfferRepository extends JpaRepository<ExchangeOffer, Integer> {
    List<ExchangeOffer> findAllByUsuarioId(Integer idUsuario);

    boolean existsByUsuarioIdAndProductoId(Integer idUsuario, Integer idProducto);

    @Query("SELECT eo FROM ExchangeOffer eo WHERE eo.producto.vendedor.id = :idVendedor")
    List<ExchangeOffer> findAllByVendedorId(@Param("idVendedor") Integer idVendedor);

    List<ExchangeOffer> findByProducto_Id(Integer idProduct);

}
