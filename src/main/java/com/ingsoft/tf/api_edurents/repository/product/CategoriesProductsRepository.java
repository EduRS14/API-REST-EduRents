package com.ingsoft.tf.api_edurents.repository.product;

import com.ingsoft.tf.api_edurents.model.entity.product.CategoriesProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriesProductsRepository extends JpaRepository<CategoriesProducts, Integer> {

    static List<CategoriesProducts> findByCategoriaId(Integer categoriaId){
        return null;
    };
}
