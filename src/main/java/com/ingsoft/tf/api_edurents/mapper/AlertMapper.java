package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.AlertDTO;
import com.ingsoft.tf.api_edurents.dto.product.ShowAlertDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Alert;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {
    public Alert toEntity(AlertDTO dto, User user, Product product) {
        Alert alert = new Alert();
        alert.setUsuario(user);
        alert.setProducto(product);
        alert.setTipo(dto.getTipo());
        alert.setMensaje(dto.getMensaje());
        alert.setVisto(false);
        return alert;
    }

    public ShowAlertDTO toResponse(Alert alert) {
        ShowAlertDTO dto = new ShowAlertDTO();
        dto.setId(alert.getId());
        dto.setTipo(alert.getTipo());
        dto.setMensaje(alert.getMensaje());
        dto.setVisto(alert.getVisto());
        dto.setFecha_creacion(alert.getFecha_creacion());

        dto.setId_usuario(alert.getUsuario().getId());
        dto.setId_producto(alert.getProducto().getId());
        dto.setNombre_producto(alert.getProducto().getNombre());

        return dto;
    }
    public AlertDTO toDTO(Alert alert) {
        AlertDTO dto = new AlertDTO();
        dto.setId_producto(alert.getProducto().getId());
        dto.setId_usuario(alert.getUsuario().getId());
        dto.setTipo(alert.getTipo());
        dto.setMensaje(alert.getMensaje());
        return dto;
    }
    public ShowAlertDTO showAlertToDTO(Alert alert) {
        ShowAlertDTO dto = new ShowAlertDTO();
        dto.setId(alert.getId());
        dto.setTipo(alert.getTipo());
        dto.setMensaje(alert.getMensaje());
        dto.setVisto(alert.getVisto());
        dto.setFecha_creacion(alert.getFecha_creacion());

        dto.setId_producto(alert.getProducto().getId());
        dto.setNombre_producto(alert.getProducto().getNombre()); // Asumiendo que el producto tiene `getNombre()`

        dto.setId_usuario(alert.getUsuario().getId());

        return dto;
    }

}
