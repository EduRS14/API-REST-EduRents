package com.ingsoft.tf.api_edurents.controller.auth.user;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthExchangeOfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/auth/exchanges")
public class UserAuthExchangeOfferController {

    private final UserAuthExchangeOfferService userAuthExchangeOfferService;

    // HU02

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ShowExchangeOfferDTO> crearIntercambio(@Valid @RequestBody ExchangeOfferDTO intercambioDTO){
        ShowExchangeOfferDTO ofertaIntercambio = userAuthExchangeOfferService.crearIntercambio(intercambioDTO);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/user/{idUser}")
    public ResponseEntity<ShowExchangeOfferDTO> obtenerIntercambioPorIdPorUsuarioId(@PathVariable Integer id, @PathVariable Integer idUser){
        ShowExchangeOfferDTO ofertaIntercambio = userAuthExchangeOfferService.obtenerIntercambioPorIdPorUsuarioId(id, idUser);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorUsuario(@PathVariable Integer id){
        List<ShowExchangeOfferDTO> ofertasIntercambio = userAuthExchangeOfferService.obtenerIntercambiosPorUsuario(id);
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<List<ShowExchangeOfferDTO>> obtenerIntercambiosPorVendedor(@PathVariable Integer id){
        List<ShowExchangeOfferDTO> ofertasIntercambio = userAuthExchangeOfferService.obtenerIntercambiosPorVendedor(id);
        return new ResponseEntity<List<ShowExchangeOfferDTO>>(ofertasIntercambio, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowExchangeOfferDTO> actualizarIntercambio(@PathVariable Integer id, @Valid @RequestBody ExchangeOfferDTO intercambioDTO){
        ShowExchangeOfferDTO ofertaIntercambio = userAuthExchangeOfferService.actualizarIntercambio(id, intercambioDTO);
        return new ResponseEntity<ShowExchangeOfferDTO>(ofertaIntercambio, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<ShowProductDTO> eliminarIntercambio(@PathVariable Integer id) {
        userAuthExchangeOfferService.eliminarIntercambio(id);
        return new ResponseEntity<ShowProductDTO>(HttpStatus.NO_CONTENT);
    }

}
