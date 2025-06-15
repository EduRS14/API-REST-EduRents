package com.ingsoft.tf.api_edurents.service.impl.admin;

import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSellerServiceImpl implements AdminSellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public SellerDTO convertToDTO(Seller seller) {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(seller.getId());
        String nombreCompleto = seller.getUsuario().getNombres() + " " + seller.getUsuario().getApellidos();
        sellerDTO.setNombreUsuario(nombreCompleto);
        sellerDTO.setResena(seller.getResena());
        sellerDTO.setConfiabilidad(seller.getConfiabilidad());
        sellerDTO.setSin_demoras(seller.getSin_demoras());
        sellerDTO.setBuena_atencion(seller.getBuena_atencion());
        return sellerDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public SellerDTO getConfiabilidadById(Integer id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + id));
        return convertToDTO(seller);
    }

    @Transactional
    @Override
    public SellerDTO updateConfiabilidad(Integer id, Boolean nuevaConfiabilidad) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado con ID: " + id));

        seller.setConfiabilidad(nuevaConfiabilidad);
        seller = sellerRepository.save(seller);

        return convertToDTO(seller);
    }
}
