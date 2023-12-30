package com.oasisuniformes.inventario.controller;

import com.oasisuniformes.inventario.entity.Combo;
import com.oasisuniformes.inventario.entity.ProductCombo;
import com.oasisuniformes.inventario.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/combos")
public class ComboController {

    private final ComboService comboService;

    @Autowired
    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @GetMapping
    public ResponseEntity<List<Combo>> getAllCombos() {
        List<Combo> combos = comboService.getAllCombos();
        return new ResponseEntity<>(combos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Combo> getComboById(@PathVariable Integer id) {
        return comboService.getComboById(id)
                .map(combo -> new ResponseEntity<>(combo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<ProductCombo>> getProductComboById(@PathVariable Integer id) {
        List<ProductCombo> productCombos = comboService.getCombosById(id);
        return new ResponseEntity<>(productCombos, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createCombo(@RequestBody Combo combo) {
        try {
            Combo savedCombo = comboService.saveCombo(combo);
            return new ResponseEntity<>(savedCombo, HttpStatus.CREATED);
        } catch (
                DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate combo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving combo: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Combo> updateCombo(@PathVariable Integer id, @RequestBody Combo combo) {
        if (!comboService.getComboById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        combo.setId(id);
        Combo updatedCombo = comboService.saveCombo(combo);
        return new ResponseEntity<>(updatedCombo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Integer id) {
        if (!comboService.getComboById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comboService.deleteCombo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
