package com.ingsoft.tf.api_edurents.service.Interface.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.AlertDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowAlertDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserAuthAlertService {


    AlertDTO createAlert(AlertDTO dto);

    void deleteAlert(Integer idAlert);

    List<ShowAlertDTO> getAlertsByUser(Integer idUser);

    @Transactional
    void markAlertAsViewed(Integer idAlert);
}
