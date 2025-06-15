package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.exchanges.ExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.exchanges.ShowExchangeOfferDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.dto.user.UserDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeOffer;
import com.ingsoft.tf.api_edurents.model.entity.exchanges.ExchangeStatus;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.exchanges.ExchangeOfferRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ExchangeOfferMapper {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ExchangeOfferRepository exchangeOfferRepository;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    public ExchangeOfferMapper(ProductRepository productRepository, UserRepository userRepository, ExchangeOfferRepository exchangeOfferRepository, UserMapper userMapper, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.exchangeOfferRepository = exchangeOfferRepository;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }

    public ExchangeOffer toEntity(ExchangeOffer intercambio, ExchangeOfferDTO intercambioDTO, String tipo) {
        intercambio.setMensaje_propuesta(intercambioDTO.getMensaje_propuesta());
        if (tipo.equals("crear")) {
            intercambio.setEstado(ExchangeStatus.PENDIENTE);
        } else if (tipo.equals("editar")) {
            intercambio.setEstado(intercambioDTO.getEstado());
        }

        Product producto = productRepository.findById(intercambioDTO.getId_producto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        intercambio.setProducto(producto);

        User usuario = userRepository.findById(intercambioDTO.getId_usuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        intercambio.setUsuario(usuario);

        exchangeOfferRepository.save(intercambio);

        return intercambio;
    }

    public ShowExchangeOfferDTO toResponse(ExchangeOffer intercambio) {
        ShowExchangeOfferDTO intercambioDTOMostrar = new ShowExchangeOfferDTO();

        intercambioDTOMostrar.setId(intercambio.getId());
        intercambioDTOMostrar.setMensaje_propuesta(intercambio.getMensaje_propuesta());
        intercambioDTOMostrar.setEstado(intercambio.getEstado());

        intercambioDTOMostrar.setUsuario(userMapper.toResponse(intercambio.getUsuario()));
        intercambioDTOMostrar.setProducto(productMapper.toResponse(intercambio.getProducto()));

        return intercambioDTOMostrar;
    }
}
