package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.ingsoft.tf.api_edurents.config.TokenProvider;
import com.ingsoft.tf.api_edurents.config.UserPrincipal;
import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.dto.user.AuthResponseDTO;
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
import com.ingsoft.tf.api_edurents.service.impl.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

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

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${google.client-id}")
    private String googleClientId;
    @Autowired
    private EmailService emailService;

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
    public AuthResponseDTO loginUsuario(LoginDTO datosLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(datosLogin.getCorreo(), datosLogin.getContrasena())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User usuario = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO authResponse = userMapper.toAuthResponse(usuario, token);

        return authResponse;


        //if (userRepository.existsUserByCorreo(datosLogin.getCorreo())){
        //    User usuario = userRepository.findByCorreoAndContrasena(datosLogin.getCorreo(), datosLogin.getContrasena());
        //    if (usuario != null) {
        //        return userMapper.toResponse(usuario);
        //    } else {
        //        throw new BadRequestException("La contraseña es incorrecta");
        //    }
        //} else {
        //    throw new BadRequestException("Credenciales inválidas");
        //}
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<?> loginGoogle(String idTokenString) {
        try {

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                User usuario = userRepository.findByCorreo(email);

                if (usuario != null) {

                    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

                    UserPrincipal userPrincipal = new UserPrincipal(
                            usuario.getId(),
                            usuario.getCorreo(),
                            usuario.getContrasena(),
                            Collections.singleton(authority),
                            usuario
                    );

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userPrincipal, null, userPrincipal.getAuthorities()
                    );

                    String token = tokenProvider.createAccessToken(authentication);

                    AuthResponseDTO authResponse = userMapper.toAuthResponse(usuario, token);
                    return ResponseEntity.ok(authResponse);

                } else {
                    return ResponseEntity.ok(Map.of(
                            "usuarioNoRegistrado", true,
                            "correo", email,
                            "nombre", name
                    ));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ID Token inválido");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Muy útil en desarrollo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verificando token de Google");
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

            try {
                emailService.enviarCorreoRecuperacion(correo, proceso.getToken_original());
            } catch (MessagingException e) {
                throw new RuntimeException("Error al enviar correo de recuperación", e);
            }


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
            return "Token válido, proceso activado correctamente";
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

            usuario.setContrasena(passwordEncoder.encode(newPassword));
            userRepository.save(usuario);

            // Marcar el proceso como no válido después de usarlo
            proceso.setValido(false);
            recoverProcessRepository.save(proceso);
        } else {
            throw new BadRequestException("Token inválido");
        }
    }

}
