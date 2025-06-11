package com.ingsoft.tf.api_edurents.controller.admin;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.admin.AdminExchangeOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/exchanges")
public class AdminExchangeOfferController {

    private final AdminExchangeOfferService adminExchangeOfferService;

    @GetMapping
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambios(){
        List<ShowExchangeOfferDTO> ofertasIntercambio = adminExchangeOfferService.obtenerTodosLosIntercambios();
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowExchangeOfferDTO> obtenerIntercambioPorId(@PathVariable Integer id){
        ShowExchangeOfferDTO ofertaIntercambio = adminExchangeOfferService.obtenerIntercambioPorId(id);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }





}
