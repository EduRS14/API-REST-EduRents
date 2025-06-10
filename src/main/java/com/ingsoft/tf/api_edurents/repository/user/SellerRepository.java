package com.ingsoft.tf.api_edurents.repository.user;

import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    // Buscar un vendedor por el ID del usuario asociado
    @Query("SELECT s FROM Seller s WHERE s.usuario.id = :idUser")
    Optional<Seller> findByUserId(@Param("idUser") Long idUser);
    // Buscar un vendedor por el nombre de usuario
    @Query("SELECT s FROM Seller s WHERE CONCAT(LOWER(s.usuario.nombres), ' ', LOWER(s.usuario.apellidos)) = LOWER(:nombreCompleto)")
    Optional<Seller> findByNombreCompletoUsuario(@Param("nombreCompleto") String nombreCompleto);
}
