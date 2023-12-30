package com.oasisuniformes.inventario.controller;

import com.oasisuniformes.inventario.entity.Sale;
import com.oasisuniformes.inventario.model.request.ProductRequest;
import com.oasisuniformes.inventario.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<?> saveSale(@RequestBody Sale sale) {
        try {
            Sale saleSaved = saleService.saveSaleWithItems(sale);
            return ResponseEntity.status(HttpStatus.CREATED).body(saleSaved);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate product: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product: " + e.getMessage());
        }
    }
}
