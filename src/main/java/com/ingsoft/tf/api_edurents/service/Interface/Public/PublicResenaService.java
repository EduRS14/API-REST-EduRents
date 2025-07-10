package com.ingsoft.tf.api_edurents.service.Interface.Public;

import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;

import java.util.List;

public interface PublicResenaService {

    List<ResenaResponseDTO> obtenerResenasPorVendedor(Integer idVendedor);

    ResenaResponseDTO obtenerResena(Integer idResena);

}
