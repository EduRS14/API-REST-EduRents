package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.RecoverPasswordDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.UserMapper;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthUserServiceImpl implements UserAuthUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CareerRepository careerRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDTO obtenerUsuarioPorId(Integer id) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        return userMapper.toResponse(usuario);
    }

    @Transactional
    @Override
    public UserDTO cambioContrasenaUsuario(Integer id, RecoverPasswordDTO nuevosDatos) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        if (!usuario.getCorreo().equals(nuevosDatos.getCorreo())) {
            throw new BadRequestException("Correo incorrecto");
        }
        if (!usuario.getContrasena().equals(nuevosDatos.getContrasena())) {
            throw new BadRequestException("Contraseña incorrecta");
        }
        if (usuario.getContrasena().equals(nuevosDatos.getNuevaContrasena())) {
            throw new BadRequestException("La nueva contraseña no puede ser igual a la anterior");
        }

        usuario.setContrasena(nuevosDatos.getNuevaContrasena());
        userRepository.save(usuario);
        return userMapper.toResponse(usuario);
    }

    @Transactional
    @Override
    public UserDTO actualizarDatosUsuario(Integer id, UserDTO usuarioDTO) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setCodigo_universitario(usuarioDTO.getCodigo_universitario());
        usuario.setCiclo(usuarioDTO.getCiclo());

        userRepository.save(usuario);
        return userMapper.toResponse(usuario);
    }

    @Transactional
    @Override
    public UserDTO cambiarFotoUsuario(Integer id, String urlFoto) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        if (urlFoto == null || urlFoto.isEmpty()) {
            throw new BadRequestException("La URL de la foto no puede estar vacía");
        }

        if (!urlFoto.startsWith("http://") && !urlFoto.startsWith("https://")) {
            throw new BadRequestException("URL no válida");
        }

        usuario.setFoto_url(urlFoto);
        userRepository.save(usuario);
        return userMapper.toResponse(usuario);
    }

    @Transactional
    @Override
    public UserDTO cambiarCarreraUsuario(Integer id, Integer idCarrera) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        if (idCarrera == null) {
            throw new BadRequestException("El ID de la carrera no puede ser nulo");
        }

        Career carrera = careerRepository.findById(idCarrera)
                .orElseThrow(() -> new ResourceNotFoundException("La carrera no existe"));

        usuario.setCarrera(carrera);
        userRepository.save(usuario);
        return userMapper.toResponse(usuario);
    }

    @Transactional
    @Override
    public UserDTO cambiarCicloUsuario(Integer id, Byte ciclo) {
        User usuario = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        if (ciclo == null || ciclo < 1 || ciclo > 10) {
            throw new BadRequestException("El ciclo debe estar entre 1 y 10");
        }

        if (usuario.getCiclo() != null && usuario.getCiclo().equals(ciclo)) {
            throw new BadRequestException("El ciclo no puede ser el mismo que el actual");
        }

        usuario.setCiclo(ciclo);
        userRepository.save(usuario);
        return userMapper.toResponse(usuario);
    }

}
