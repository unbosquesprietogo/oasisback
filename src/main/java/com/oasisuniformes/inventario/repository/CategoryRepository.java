package com.oasisuniformes.inventario.repository;

import com.oasisuniformes.inventario.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
