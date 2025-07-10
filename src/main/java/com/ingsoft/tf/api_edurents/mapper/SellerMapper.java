package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerReputationDTO;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerMapper {

    public Seller toEntity(Integer id) {
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }

    public SellerDTO toSellerDTO(Seller seller) {
        SellerDTO dto = new SellerDTO();

        dto.setId(seller.getId());
        dto.setPresentacion(seller.getPresentacion());
        dto.setConfiabilidad(seller.getConfiabilidad());
        dto.setSin_demoras(seller.getSin_demoras());
        dto.setBuena_atencion(seller.getBuena_atencion());
        dto.setNombreUsuario(seller.getNombre_usuario());
        dto.setNombreNegocio(seller.getNombre_negocio());
        dto.setCorreoElectronico(seller.getCorreo_electronico());
        dto.setNumeroTelefono(seller.getNumero_telefono());

        return dto;
    }

    public SellerReputationDTO toSellerReputationDTO(Seller seller) {
        SellerReputationDTO dto = new SellerReputationDTO();

        dto.setPresentacion(seller.getPresentacion());
        dto.setConfiabilidad(seller.getConfiabilidad());
        dto.setSin_demoras(seller.getSin_demoras());
        dto.setBuena_atencion(seller.getBuena_atencion());

        return dto;
    }

}
