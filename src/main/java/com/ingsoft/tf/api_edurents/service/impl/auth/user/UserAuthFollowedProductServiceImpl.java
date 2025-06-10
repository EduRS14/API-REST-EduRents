package com.ingsoft.tf.api_edurents.service.impl.auth.user;

import com.ingsoft.tf.api_edurents.dto.product.ShowProductDTO;
import com.ingsoft.tf.api_edurents.model.entity.product.FollowedProduct;
import com.ingsoft.tf.api_edurents.model.entity.product.Product;
import com.ingsoft.tf.api_edurents.model.entity.user.User;
import com.ingsoft.tf.api_edurents.repository.product.FollowedProductRepository;
import com.ingsoft.tf.api_edurents.repository.product.ProductRepository;
import com.ingsoft.tf.api_edurents.repository.user.UserRepository;
import com.ingsoft.tf.api_edurents.service.Interface.auth.user.UserAuthFollowedProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthFollowedProductServiceImpl implements UserAuthFollowedProductService {
    private final FollowedProductRepository followedProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public void followProduct(Integer idUser, Integer idProduct) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id " + idUser));

        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id " + idProduct));

        boolean alreadyFollowing = followedProductRepository.existsByUsuarioIdAndProductoId(idUser, idProduct);
        if (!alreadyFollowing) {
            FollowedProduct followedProduct = new FollowedProduct();
            followedProduct.setUsuario(user);
            followedProduct.setProducto(product);
            followedProduct.setFecha_inicio_seguimiento(LocalDateTime.now().toLocalDate());
            followedProductRepository.save(followedProduct);
        }
    }

    @Override
    public void unfollowProduct(Integer idUser, Integer idProduct) {
        FollowedProduct followedProduct = followedProductRepository
                .findByUsuarioIdAndProductoId(idUser, idProduct)
                .orElseThrow(() -> new EntityNotFoundException("No se sigue este producto"));

        followedProductRepository.delete(followedProduct);
    }
    @Override
    public List<ShowProductDTO> getFollowedProductsByUser(Integer idUser) {
        List<FollowedProduct> followed = followedProductRepository.findByUsuarioId(idUser);

        return followed.stream().map(fp -> {
            ShowProductDTO dto = new ShowProductDTO();
            dto.setId(fp.getProducto().getId());
            dto.setNombre(fp.getProducto().getNombre());
            dto.setDescripcion(fp.getProducto().getDescripcion());
            //dto.setCategorias(fp.getProducto().getCategorias().);// Ajusta según modelo

            dto.setEstado(fp.getProducto().getEstado()); // Enum
            return dto;
        }).collect(Collectors.toList());
    }
}
