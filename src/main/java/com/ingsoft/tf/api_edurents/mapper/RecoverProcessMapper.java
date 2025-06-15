package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.auth.RecoverProcessDTO;
import com.ingsoft.tf.api_edurents.model.entity.auth.RecoverProcess;
import com.ingsoft.tf.api_edurents.repository.auth.RecoverProcessRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RecoverProcessMapper {

    private final RecoverProcessRepository recoverProcessRepository;

    public RecoverProcessMapper(RecoverProcessRepository recoverProcessRepository) {
        this.recoverProcessRepository = recoverProcessRepository;
    }

    public RecoverProcess toEntity(String email) {

        RecoverProcess recoverProcess = new RecoverProcess();
        recoverProcess.setCorreo(email);
        recoverProcess.setValido(true);
        recoverProcess.setActivado(false);
        recoverProcess.setFechaCreacion(LocalDateTime.now());
        recoverProcess.setFechaExpiracion(LocalDateTime.now().plusHours(1));

        String token = UUID.randomUUID().toString();
        String tokenHash = BCrypt.hashpw(token, BCrypt.gensalt());

        recoverProcess.setToken_original(token);
        recoverProcess.setToken_hasheado(tokenHash);

        recoverProcessRepository.save(recoverProcess);

        return recoverProcess;
    }

    public RecoverProcessDTO toResponse(RecoverProcess proceso) {
        RecoverProcessDTO dto = new RecoverProcessDTO();
        dto.setId(proceso.getId());
        dto.setCorreo(proceso.getCorreo());
        dto.setToken_original(proceso.getToken_original());
        dto.setFechaExpiracion(proceso.getFechaExpiracion());
        return dto;
    }

}
