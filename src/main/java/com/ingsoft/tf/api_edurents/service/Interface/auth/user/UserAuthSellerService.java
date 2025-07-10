package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.RegisterSellerDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;

import java.util.List;

public interface UserAuthSellerService {



    SellerDTO createSellerIfNotExists(Integer idUser, RegisterSellerDTO registerSellerDTO);

    List<ShowTransactionDTO> getTransactionsBySeller(Integer idSeller);

    //PublicSellerProfileDTO getPublicSellerProfile(Long idSeller);

}
