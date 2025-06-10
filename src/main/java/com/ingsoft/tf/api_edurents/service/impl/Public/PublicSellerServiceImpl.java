package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.product.ProductDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerReputationDTO;
import com.ingsoft.tf.api_edurents.mapper.ProductMapper;
import com.ingsoft.tf.api_edurents.mapper.SellerMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicSellerServiceImpl implements PublicSellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final SellerMapper sellerMapper;
    private final ProductMapper productMapper;

    @Override
    public SellerDTO getPublicSellerProfile(Integer idSeller) {
        Seller seller = sellerRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
        return sellerMapper.toSellerDTO(seller);
    }


    // Endpoint 04: GET /sellers/name/{name}
    @Override
    public SellerDTO getSellerByNombreUsuario(String nombreUsuario) {
        Seller seller = sellerRepository.findByNombreCompletoUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
        return sellerMapper.toSellerDTO(seller);
    }

    // Endpoint 05: GET /sellers/{idSeller}/reputation
    @Override
    public SellerReputationDTO getSellerReputation(Integer idSeller) {
        Seller seller = sellerRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        return sellerMapper.toSellerReputationDTO(seller);
    }

    // Endpoint 06: GET /sellers/{idSeller}/products
    @Override
    public List<ShowProductDTO> getProductsBySeller(Integer idSeller) {
        Seller seller = sellerRepository.findById(idSeller)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        return productRepository.findByVendedor(idSeller)
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }


}
