package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final CareerRepository careerRepository;

    public UserMapper(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    public User toEntity(RegisterDTO request) {
        User user = new User();

        user.setNombres(request.getNombres());
        user.setApellidos(request.getApellidos());
        user.setCorreo(request.getCorreo());
        user.setContrasena(request.getContrasena());
        user.setCodigo_universitario(request.getCodigo_universitario());
        user.setCiclo(request.getCiclo());

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

        if (user.getCarrera() != null) {
            dto.setCarrera(user.getCarrera().getNombre());
        } else {
            dto.setCarrera(null);
        }

        return dto;
    }

}
