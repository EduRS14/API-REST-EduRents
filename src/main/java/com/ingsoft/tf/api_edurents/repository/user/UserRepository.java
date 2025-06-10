package com.ingsoft.tf.api_edurents.repository.user;

import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT count(u) > 0 FROM User u WHERE u.correo =:correo")
    boolean existsUserByCorreo(@Param("correo") String correo);

    @Query("SELECT u FROM User u WHERE u.correo =:correo")
    User findByCorreo(@Param("correo") String correo);

    @Query("SELECT u FROM User u WHERE u.correo =:correo AND u.contrasena =:contrasena")
    User findByCorreoAndContrasena(@Param("correo") String correo, @Param("contrasena") String contrasena);
  
}
