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
        dto.setResena(seller.getResena());
        dto.setConfiabilidad(seller.getConfiabilidad());
        dto.setSin_demoras(seller.getSin_demoras());
        dto.setBuena_atencion(seller.getBuena_atencion());

        if (seller.getUsuario() != null) {
            String nombreCompleto = seller.getUsuario().getNombres() + " " + seller.getUsuario().getApellidos();
            dto.setNombreUsuario(nombreCompleto);
        } else {
            dto.setNombreUsuario("");
        }

        return dto;
    }
    public SellerReputationDTO toSellerReputationDTO(Seller seller) {
        SellerReputationDTO dto = new SellerReputationDTO();

        dto.setResena(seller.getResena());
        dto.setConfiabilidad(seller.getConfiabilidad());
        dto.setSin_demoras(seller.getSin_demoras());
        dto.setBuena_atencion(seller.getBuena_atencion());

        return dto;
    }
}
