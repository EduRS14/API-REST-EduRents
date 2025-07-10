package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.user.ResenaRequestDTO;
import com.ingsoft.tf.api_edurents.dto.user.ResenaResponseDTO;
import com.ingsoft.tf.api_edurents.exception.ResourceNotFoundException;
import com.ingsoft.tf.api_edurents.mapper.ResenaMapper;
import com.ingsoft.tf.api_edurents.model.entity.user.Resena;
import com.ingsoft.tf.api_edurents.model.entity.user.Seller;
import com.ingsoft.tf.api_edurents.repository.user.ResenaRepository;
import com.ingsoft.tf.api_edurents.repository.user.SellerRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthResenaServiceImpl implements UserAuthResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ResenaMapper resenaMapper;

    @Autowired
    private SellerRepository sellerRepository;

    private void modificarDatosVendedor(Integer idVendedor) {

        int cantidadBuenaConfiabilidad = 0;
        int cantidadSinDemoras = 0;
        int cantidadBuenaAtencion = 0;

        Seller vendedor = sellerRepository.findById(idVendedor)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor no encontrado"));

        List<Resena> resenas = resenaRepository.obtenerResenasPorIdVendedor(idVendedor);
        int cantidadResenas = resenas.size();

        for (Resena resena : resenas) {
            if (resena.getConfiabilidad()) {
                cantidadBuenaConfiabilidad++;
            }
            if (resena.getSin_demoras()) {
                cantidadSinDemoras++;
            }
            if (resena.getBuena_atencion()) {
                cantidadBuenaAtencion++;
            }
        }

        if (cantidadResenas > 0) {
            int cantidadBuenaConfiabilidadPorcentaje = (int) ((double) cantidadBuenaConfiabilidad / cantidadResenas * 100);
            vendedor.setConfiabilidad(cantidadBuenaConfiabilidadPorcentaje >= 50);
            int cantidadSinDemorasPorcentaje = (int) ((double) cantidadSinDemoras / cantidadResenas * 100);
            vendedor.setSin_demoras(cantidadSinDemorasPorcentaje >= 50);
            int cantidadBuenaAtencionPorcentaje = (int) ((double) cantidadBuenaAtencion / cantidadResenas * 100);
            vendedor.setBuena_atencion(cantidadBuenaAtencionPorcentaje >= 50);
        } else {
            vendedor.setConfiabilidad(false);
            vendedor.setBuena_atencion(false);
            vendedor.setSin_demoras(false);
        }
        sellerRepository.save(vendedor);

    }

    @Transactional(readOnly = true)
    @Override
    public ResenaResponseDTO obtenerResenaPorIdVendedorYIDUsuario(Integer idVendedor, Integer idUsuario) {
        Resena resena = resenaRepository.obtenerResenaPorIdVendedorYIDUsuario(idVendedor, idUsuario);
        if (resena == null) {
            throw new ResourceNotFoundException("Resena no encontrada");
        }
        return resenaMapper.toResponse(resena);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResenaResponseDTO> obtenerResenasPorVendedorYNoMismoUsuario(Integer idVendedor, Integer idUsuario) {
        List<Resena> resenas = resenaRepository.obtenerResenasPorVendedorYNoMismoUsuario(idVendedor, idUsuario);
        return resenas.stream().map(resenaMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean resenaExistentePorIdVendedorYIDUsuario(Integer idVendedor, Integer idUsuario) {
        return resenaRepository.resenaExistentePorIdVendedorYIDUsuario(idVendedor, idUsuario);
    }

    @Transactional
    @Override
    public ResenaResponseDTO crearResena(ResenaRequestDTO request) {
        Resena resena = new Resena();
        resena = resenaMapper.toEntity(resena, request);
        Resena savedResena = resenaRepository.save(resena);
        modificarDatosVendedor(savedResena.getVendedor().getId());
        return resenaMapper.toResponse(savedResena);
    }

    @Transactional
    @Override
    public ResenaResponseDTO actualizarResena(Integer idResena, ResenaRequestDTO request) {
        Resena resena = resenaRepository.findById(idResena)
                .orElseThrow(() -> new ResourceNotFoundException("Resena no encontrada"));
        resena = resenaMapper.toEntity(resena, request);
        Resena updatedResena = resenaRepository.save(resena);
        modificarDatosVendedor(updatedResena.getVendedor().getId());
        return resenaMapper.toResponse(updatedResena);
    }

    @Transactional
    @Override
    public void eliminarResena(Integer idResena) {
        Resena resena = resenaRepository.findById(idResena)
                .orElseThrow(() -> new ResourceNotFoundException("Resena no encontrada"));
        int idVendedor = resena.getVendedor().getId();
        resenaRepository.delete(resena);
        modificarDatosVendedor(idVendedor);
    }

}
