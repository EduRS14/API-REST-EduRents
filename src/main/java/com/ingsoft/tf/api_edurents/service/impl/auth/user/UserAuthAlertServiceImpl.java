package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.AlertDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowAlertDTO;
import com.ingsoft.tf.api_edurents.mapper.AlertMapper;
import com.ingsoft.tf.api_edurents.model.entity.product.Alert;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.AlertRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthAlertService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthAlertServiceImpl implements UserAuthAlertService {
    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public AlertDTO createAlert(AlertDTO dto) {
        User user = userRepository.findById(dto.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Product product = productRepository.findById(dto.getId_producto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Alert alert = alertMapper.toEntity(dto,user, product);
        alert.setUsuario(user);
        alert.setProducto(product);

        return alertMapper.toDTO(alertRepository.save(alert));
    }

    @Override
    public void deleteAlert(Integer idAlert) {
        alertRepository.deleteById(idAlert);
    }

    @Override
    public List<ShowAlertDTO> getAlertsByUser(Integer idUser) {
        return alertRepository.findByUsuarioId(idUser)
                .stream()
                .sorted(Comparator.comparing(Alert::getFecha_creacion).reversed())
                .map(alertMapper::showAlertToDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void markAlertAsViewed(Integer idAlert) {
        alertRepository.markAsViewed(idAlert);
    }
}


