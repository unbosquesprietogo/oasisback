package com.oasisuniformes.inventario.repository;

import com.oasisuniformes.inventario.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
}
