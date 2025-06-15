package com.ingsoft.tf.api_edurents.repository.auth;

import com.ingsoft.tf.api_edurents.model.entity.auth.RecoverProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecoverProcessRepository extends JpaRepository<RecoverProcess, Integer> {

    @Query("SELECT rp FROM RecoverProcess rp WHERE rp.correo = :correo AND rp.valido = :valido")
    RecoverProcess findByCorreoAndValido(@Param("correo") String correo, @Param("valido") boolean valido);
}
