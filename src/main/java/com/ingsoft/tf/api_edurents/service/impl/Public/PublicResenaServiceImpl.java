package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ResenaMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.Resena;
import com.ingsoft.tf.api_edurents.repository.user.ResenaRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicResenaServiceImpl implements PublicResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ResenaMapper resenaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ResenaResponseDTO> obtenerResenasPorVendedor(Integer idVendedor) {
        List<Resena> resenas = resenaRepository.obtenerResenasPorIdVendedor(idVendedor);

        if (resenas.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado reseñas para el vendedor con ID: " + idVendedor);
        }

        return resenas.stream()
                .map(resenaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ResenaResponseDTO obtenerResena(Integer idResena) {
        Resena resena = resenaRepository.findById(idResena)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada con ID: " + idResena));
        return resenaMapper.toResponse(resena);
    }

}
