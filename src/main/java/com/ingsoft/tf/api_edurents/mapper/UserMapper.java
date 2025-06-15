package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.AuthResponseDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.exception.RoleNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.user.Role;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.model.entity.user.UserRole;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.repository.user.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final CareerRepository careerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserMapper(CareerRepository careerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.careerRepository = careerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterDTO request) {
        User user = new User();

        user.setNombres(request.getNombres());
        user.setApellidos(request.getApellidos());
        user.setCorreo(request.getCorreo());
        user.setCodigo_universitario(request.getCodigo_universitario());
        user.setCiclo(request.getCiclo());

        Role role = roleRepository.findByNombre(UserRole.USER)
                .orElseThrow(() -> new RoleNotFoundException("El rol de usuario no existe"));

        user.setRol(role);

        user.setContrasena(passwordEncoder.encode(request.getContrasena()));

        Career carrera = careerRepository.findById(request.getId_carrera())
                .orElseThrow(() -> new ResourceNotFoundException("La carrera no existe"));
        user.setCarrera(carrera);
        user.setFoto_url(request.getFoto_url());

        return user;
    }

    public UserDTO toResponse(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setNombres(user.getNombres());
        dto.setApellidos(user.getApellidos());
        dto.setCorreo(user.getCorreo());
        dto.setCodigo_universitario(user.getCodigo_universitario());
        dto.setCiclo(user.getCiclo());
        dto.setFoto_url(user.getFoto_url());
        dto.setRol(user.getRol().getNombre());

        if (user.getCarrera() != null) {
            dto.setCarrera(user.getCarrera().getNombre());
        } else {
            dto.setCarrera(null);
        }

        return dto;
    }

    public AuthResponseDTO toAuthResponse(User user, String token) {
        AuthResponseDTO authDto = new AuthResponseDTO();

        authDto.setId(user.getId());
        authDto.setNombres(user.getNombres());
        authDto.setApellidos(user.getApellidos());
        authDto.setCorreo(user.getCorreo());
        authDto.setCodigoUniversitario(user.getCodigo_universitario());
        authDto.setCiclo(user.getCiclo());
        authDto.setFotoUrl(user.getFoto_url());
        authDto.setRol(user.getRol().getNombre().toString());

        if (user.getCarrera() != null) {
            authDto.setCarrera(user.getCarrera().getNombre());
        } else {
            authDto.setCarrera(null);
        }

        authDto.setToken(token);

        return authDto;
    }

}
