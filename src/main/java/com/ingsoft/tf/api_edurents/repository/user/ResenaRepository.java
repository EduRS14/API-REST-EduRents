package com.ingsoft.tf.api_edurents.repository.user;

import com.ingsoft.tf.api_edurents.model.entity.user.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Resena r WHERE r.vendedor.id = :idVendedor AND r.usuario.id = :idUsuario")
    Boolean resenaExistentePorIdVendedorYIDUsuario(@Param("idVendedor") Integer idVendedor, @Param("idUsuario") Integer idUsuario);

    @Query("SELECT r FROM Resena r WHERE r.vendedor.id = :idVendedor")
    List<Resena> obtenerResenasPorIdVendedor(@Param("idVendedor") Integer idVendedor);

    @Query("SELECT r FROM Resena r WHERE r.vendedor.id = :idVendedor AND r.usuario.id = :idUsuario")
    Resena obtenerResenaPorIdVendedorYIDUsuario(@Param("idVendedor") Integer idVendedor, @Param("idUsuario") Integer idUsuario);

    @Query("SELECT r FROM Resena r WHERE r.usuario.id != :idUsuario AND r.vendedor.id = :idVendedor")
    List<Resena> obtenerResenasPorVendedorYNoMismoUsuario(@Param("idVendedor") Integer idVendedor, @Param("idUsuario") Integer idUsuario);

}
