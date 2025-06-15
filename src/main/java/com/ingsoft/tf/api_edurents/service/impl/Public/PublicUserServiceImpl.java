package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.user.LoginDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.RecoverProcessMapper;
import com.ingsoft.tf.api_edurents.mapper.UserMapper;
import com.ingsoft.tf.api_edurents.model.entity.auth.RecoverProcess;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.auth.RecoverProcessRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PublicUserServiceImpl implements PublicUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecoverProcessRepository recoverProcessRepository;

    @Autowired
    private RecoverProcessMapper recoverProcessMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public UserDTO registerUsuario(RegisterDTO datosRegistro) {
        if (!userRepository.existsUserByCorreo(datosRegistro.getCorreo())) {
            User usuario = userMapper.toEntity(datosRegistro);
            userRepository.save(usuario);
            return userMapper.toResponse(usuario);
        } else {
            throw new BadRequestException("El correo ya está registrado en otra cuenta");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO loginUsuario(LoginDTO datosLogin) {
        if (userRepository.existsUserByCorreo(datosLogin.getCorreo())){
            User usuario = userRepository.findByCorreoAndContrasena(datosLogin.getCorreo(), datosLogin.getContrasena());
            if (usuario != null) {
                return userMapper.toResponse(usuario);
            } else {
                throw new BadRequestException("La contraseña es incorrecta");
            }
        } else {
            throw new BadRequestException("Credenciales inválidas");
        }
    }

    // Implementación de los métodos de recuperación de contraseña
    @Transactional
    @Override
    public RecoverProcessDTO forgotPassword(String correo) {
        if (userRepository.existsUserByCorreo(correo)) {
            // Verificar si ya existe un proceso de recuperación activo para este correo
            RecoverProcess existingProcess = recoverProcessRepository.findByCorreoAndValido(correo, true);
            if (existingProcess != null) {
                // Si ya existe un proceso activo, lo hacemos no válido
                existingProcess.setValido(false);
                recoverProcessRepository.save(existingProcess);
            }
            RecoverProcess proceso = recoverProcessMapper.toEntity(correo);
            return recoverProcessMapper.toResponse(proceso);
        } else {
            throw new BadRequestException("El correo no pertenece a ningún usuario registrado");
        }
    }

    @Transactional
    @Override
    public String verifyToken(Integer id, String token) {
        RecoverProcess proceso = recoverProcessRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El proceso de recuperación no existe"));

        if (!proceso.getValido()) {
            throw new BadRequestException("El proceso de recuperación ya no es válido");
        }

        if (proceso.getActivado()){
            throw new BadRequestException("El proceso de recuperación ya ha sido activado");
        }

        if (proceso.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("El proceso de recuperación ha expirado");
        }

        Boolean isTokenValid = BCrypt.checkpw(token, proceso.getToken_hasheado());

        if (isTokenValid) {
            // Marcar el proceso como activado
            proceso.setActivado(true);
            recoverProcessRepository.save(proceso);
            return "Token válido";
        } else {
            throw new BadRequestException("Token inválido");
        }
    }

    @Transactional
    @Override
    public void resetPassword(Integer id, String token, String newPassword) {
        RecoverProcess proceso = recoverProcessRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El proceso de recuperación no existe"));

        if (!proceso.getValido()) {
            throw new BadRequestException("El proceso de recuperación ya no es válido");
        }

        if (!proceso.getActivado()) {
            throw new BadRequestException("El proceso de recuperación no ha sido activado");
        }

        if (proceso.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("El proceso de recuperación ha expirado");
        }

        Boolean isTokenValid = BCrypt.checkpw(token, proceso.getToken_hasheado());

        if (isTokenValid) {
            User usuario = userRepository.findByCorreo(proceso.getCorreo());

            if (usuario == null) {
                throw new ResourceNotFoundException("El usuario no existe");
            }

            usuario.setContrasena(newPassword);
            userRepository.save(usuario);

            // Marcar el proceso como no válido después de usarlo
            proceso.setValido(false);
            recoverProcessRepository.save(proceso);
        } else {
            throw new BadRequestException("Token inválido");
        }
    }

}
