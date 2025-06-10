package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.transfers.ShowTransactionDTO;
import com.ingsoft.tf.api_edurents.dto.user.SellerDTO;
import com.ingsoft.tf.api_edurents.mapper.SellerMapper;
import com.ingsoft.tf.api_edurents.mapper.TransactionsMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.transfers.TransactionRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthSellerServiceImpl implements UserAuthSellerService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionsMapper transactionMapper;

    // private final ReviewRepository reviewRepository;

    @Override
    public SellerDTO createSellerIfNotExists(Integer idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return sellerRepository.findByUserId(idUser.longValue())
                .map(sellerMapper::toSellerDTO)
                .orElseGet(() -> {
                    Seller newSeller = new Seller();
                    newSeller.setUsuario(user);
                    //Valores por defecto de creacion
                    newSeller.setBuena_atencion(false);
                    newSeller.setConfiabilidad(false);
                    newSeller.setResena("Sin reseña aún");
                    newSeller.setSin_demoras(false);
                    // newSeller.setCreatedAt(LocalDateTime.now());
                    return sellerMapper.toSellerDTO(sellerRepository.save(newSeller));
                });
    }

    @Override
    public List<ShowTransactionDTO> getTransactionsBySeller(Integer idSeller) {
        return transactionRepository.findAllBySellerId(idSeller)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }

}
