package com.ingsoft.tf.api_edurents.service.impl.admin;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.exception.BadRequestException;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ExchangeOfferMapper;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminExchangeOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminExchangeOfferServiceImpl implements AdminExchangeOfferService {

    @Autowired
    private ExchangeOfferRepository exchangeOfferRepository;

    @Autowired
    private ExchangeOfferMapper exchangeOfferMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ShowExchangeOfferDTO> obtenerTodosLosIntercambios(){
        List<ExchangeOffer> intercambios = exchangeOfferRepository.findAll();
        return intercambios.stream()
                .map(exchangeOffer -> exchangeOfferMapper.toResponse(exchangeOffer))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ShowExchangeOfferDTO obtenerIntercambioPorId(Integer id) {
        ExchangeOffer intercambio = exchangeOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intercambio no encontrado con ID: " + id));
        return exchangeOfferMapper.toResponse(intercambio);
    }





}
