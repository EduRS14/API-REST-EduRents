package com.ingsoft.tf.api_edurents.repository.user;

import com.ingsoft.tf.api_edurents.model.entity.user.Role;
import com.ingsoft.tf.api_edurents.model.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNombre(UserRole nombre);
}
