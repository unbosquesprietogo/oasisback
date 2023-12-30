package com.oasisuniformes.inventario.service;

import com.oasisuniformes.inventario.entity.Sale;
import com.oasisuniformes.inventario.entity.SaleItem;
import com.oasisuniformes.inventario.repository.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleItemService {

    @Autowired
    private  SaleItemRepository saleItemRepository;



    public SaleItem saveSaleItem(SaleItem saleItem) {
        // Add any business logic or validation before saving
        return saleItemRepository.save(saleItem);
    }

    public List<SaleItem> saveAllItems(List<SaleItem> saleItems){
        return saleItemRepository.saveAll(saleItems);
    }

    public List<SaleItem> saveAllItemsBySale(Sale sale){

        List<SaleItem> items = sale.getItems();

        int id = sale.getId();
        Date date = sale.getSaleDate();
        BigDecimal totalPrice = sale.getTotalPrice();


        for (SaleItem item : items) {
            item.setSale(new Sale(id,date,totalPrice,null));
        }

        return saleItemRepository.saveAll(items);
    }

    public Optional<SaleItem> getSaleItemById(Integer id) {
        return saleItemRepository.findById(id);
    }

    // Add more methods as needed
}