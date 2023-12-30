package com.oasisuniformes.inventario.service;

import com.oasisuniformes.inventario.entity.Combo;
import com.oasisuniformes.inventario.entity.ProductCombo;
import com.oasisuniformes.inventario.repository.ComboRepository;
import com.oasisuniformes.inventario.repository.ProductComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ComboService {

    @Autowired
    private ComboRepository comboRepository;
    @Autowired
    private ProductComboRepository productComboRepository;


    public List<Combo> getAllCombos() {
        return comboRepository.findAll();
    }

    public Optional<Combo> getComboById(Integer id) {
        return comboRepository.findById(id);
    }

    public  List<ProductCombo> getCombosById(Integer id){
        return productComboRepository.findByCombo_Id(id);
    }

    public Combo saveCombo(Combo combo) {
        if(combo.getPrice().equals(new BigDecimal(0))){
            combo.updatePrice();
        }

        List<ProductCombo> productCombos = combo.getProductCombos();

        combo.setProductCombos(null);
        Combo comboSaved = comboRepository.save(combo);


        for (ProductCombo productCombo: productCombos){
            productCombo.setCombo(comboSaved);
        }

        List<ProductCombo> productComboSaved = productComboRepository.saveAll(productCombos);

        return new Combo(comboSaved.getId(),comboSaved.getName(),comboSaved.getPrice(),productComboSaved);
    }

    public void deleteCombo(Integer id) {
        comboRepository.deleteById(id);
    }
}
