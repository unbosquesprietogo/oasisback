package com.oasisuniformes.inventario.repository;

import com.oasisuniformes.inventario.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Integer> {
}
