package com.ingsoft.tf.api_edurents.service.impl.Public;

import com.ingsoft.tf.api_edurents.dto.university.CareerDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.model.entity.university.Career;
import com.ingsoft.tf.api_edurents.repository.university.CareerRepository;
import com.ingsoft.tf.api_edurents.service.Interface.Public.PublicCareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCareerServiceImpl implements PublicCareerService {

    private final CareerRepository careerRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CareerDTO> getAllCareers() {
        List<Career> careers = careerRepository.findAll();
        if (careers.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado carreras disponibles");
        }

        return careers.stream()
                .map(career -> {
                    CareerDTO careerDTO = new CareerDTO();
                    careerDTO.setId(career.getId());
                    careerDTO.setNombre(career.getNombre());
                    return careerDTO;
                }).toList();

    }

}
