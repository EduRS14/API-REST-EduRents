package com.ingsoft.tf.api_edurents.mapper;

import com.ingsoft.tf.api_edurents.dto.product.ImageDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageDTO toImageDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        return dto;
    }

    public Image toEntity(ImageDTO dto) {
        Image image = new Image();
        image.setId(dto.getId());
        image.setUrl(dto.getUrl());
        return image;
    }

}
