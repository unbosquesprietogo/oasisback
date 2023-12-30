package com.oasisuniformes.inventario.service;

import com.oasisuniformes.inventario.entity.Sale;
import com.oasisuniformes.inventario.entity.SaleItem;
import com.oasisuniformes.inventario.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class SaleService {


    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleItemService saleItemService;


    public Sale saveSaleWithItems(Sale sale) {

        Date date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        sale.setSaleDate(date);
        // Save the sale to generate its ID
        Sale savedSale = saleRepository.save(sale);

        saleItemService.saveAllItemsBySale(savedSale);

        return savedSale;
    }


    public Optional<Sale> getSaleById(Integer id) {
        return saleRepository.findById(id);
    }
}


