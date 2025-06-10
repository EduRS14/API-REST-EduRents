package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.Alert;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
    // Buscar alertas por el ID del usuario
    @Query("SELECT a FROM Alert a " +
            "JOIN FETCH a.producto p " +
            "JOIN FETCH a.usuario u " +
            "WHERE u.id = :idUser")
    List<Alert> findByUsuarioId(Integer idUser);


    @Modifying
    @Transactional
    @Query("UPDATE Alert a SET a.visto = true WHERE a.id = :idAlert")
    void markAsViewed(Integer idAlert);

}
