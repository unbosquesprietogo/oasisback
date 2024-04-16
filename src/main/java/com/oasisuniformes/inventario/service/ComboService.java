package com.oasisuniformes.inventario.service;

import com.oasisuniformes.inventario.entity.Combo;
import com.oasisuniformes.inventario.entity.Product;
import com.oasisuniformes.inventario.entity.ProductCombo;
import com.oasisuniformes.inventario.entity.ProductDetail;
import com.oasisuniformes.inventario.model.request.ComboRequest;
import com.oasisuniformes.inventario.model.request.ProductRequest;
import com.oasisuniformes.inventario.repository.ComboRepository;
import com.oasisuniformes.inventario.repository.ProductComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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


    public List<ComboRequest> getAllCombos() {
        List<Combo> combos = comboRepository.findAll();
        List<ComboRequest> comboRequests = new ArrayList<>();

        for (Combo combo: combos){
            comboRequests.add(new ComboRequest(combo.getId(),combo.getName(),combo.getPrice(),
                    getCombosById(combo.getId())));
        }
        return comboRequests;
    }

    public Optional<ComboRequest> getComboById(Integer id) {
        Optional<Combo> comboOpt = comboRepository.findById(id);
        ComboRequest comboRequest = new ComboRequest(comboOpt.get().getId(),comboOpt.get().getName(),
                comboOpt.get().getPrice(),null);
        if (comboOpt.isPresent()) {
            Combo combo = comboOpt.get();
            List<ProductCombo> productCombos = getCombosById(combo.getId());

            comboRequest.setProductCombos(productCombos);
            return Optional.of(comboRequest);
        }
        return Optional.of(comboRequest);
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
