package com.oasisuniformes.inventario.repository;

import com.oasisuniformes.inventario.entity.ProductCombo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductComboRepository extends JpaRepository<ProductCombo, Integer> {
    List<ProductCombo> findByCombo_Id(Integer id);

}
