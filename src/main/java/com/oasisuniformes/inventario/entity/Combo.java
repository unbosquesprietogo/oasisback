package com.oasisuniformes.inventario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "combo")
public class Combo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "combo_product",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<ProductCombo> productCombos = new ArrayList<>();

    public void updatePrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ProductCombo productCombo : productCombos) {
            totalPrice = totalPrice.add(productCombo.getNewPrice());
        }
        this.setPrice(totalPrice);
    }




}
