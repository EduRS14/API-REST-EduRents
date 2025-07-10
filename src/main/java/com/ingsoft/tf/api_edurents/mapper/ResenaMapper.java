package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.ResenaRequestDTO;
import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.user.Resena;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.user.ResenaRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import org.springframework.stereotype.Component;

import java.lang.module.ResolutionException;
import java.time.LocalDate;

@Component
public class ResenaMapper {


    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ResenaRepository resenaRepository;

    public ResenaMapper(UserRepository userRepository, SellerRepository sellerRepository, ResenaRepository resenaRepository) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.resenaRepository = resenaRepository;
    }

    public Resena toEntity(Resena base, ResenaRequestDTO request) {
        base.setContenido(request.getContenido());
        base.setConfiabilidad(request.getConfiabilidad());
        base.setSin_demoras(request.getSinDemoras());
        base.setBuena_atencion(request.getBuenaAtencion());
        base.setFecha(LocalDate.now());

        User usuario = userRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getIdUsuario()));
        base.setUsuario(usuario);

        Seller vendedor = sellerRepository.findById(request.getIdVendedor())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + request.getIdVendedor()));
        base.setVendedor(vendedor);


        return base;
    }

    public ResenaResponseDTO toResponse(Resena base) {
        ResenaResponseDTO response = new ResenaResponseDTO();
        response.setId(base.getId());
        response.setContenido(base.getContenido());
        response.setConfiabilidad(base.getConfiabilidad());
        response.setSinDemoras(base.getSin_demoras());
        response.setBuenaAtencion(base.getBuena_atencion());
        response.setFecha(base.getFecha());

        if (base.getUsuario() != null) {
            response.setNombreUsuario(base.getUsuario().getNombres() + " " + base.getUsuario().getApellidos());
        } else {
            throw new ResourceNotFoundException("El usuario de la reseña no existe");
        }

        return response;
    }
}
