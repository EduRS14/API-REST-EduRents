package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.ResenaRequestDTO;
import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;
import com.ingsoft.tf.api_edurents.model.entity.user.Resena;

import java.util.List;

public interface UserAuthResenaService {

    ResenaResponseDTO obtenerResenaPorIdVendedorYIDUsuario(Integer idVendedor, Integer idUsuario);

    List<ResenaResponseDTO> obtenerResenasPorVendedorYNoMismoUsuario(Integer idVendedor, Integer idUsuario);

    Boolean resenaExistentePorIdVendedorYIDUsuario(Integer idVendedor, Integer idUsuario);

    ResenaResponseDTO crearResena(ResenaRequestDTO request);

    ResenaResponseDTO actualizarResena(Integer idResena, ResenaRequestDTO request);

    void eliminarResena(Integer idResena);

}
