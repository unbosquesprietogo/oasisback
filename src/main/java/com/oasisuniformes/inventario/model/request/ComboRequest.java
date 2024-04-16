package com.oasisuniformes.inventario.model.request;

import com.oasisuniformes.inventario.entity.ProductCombo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComboRequest {

    private Integer id;

    private String name;

    private BigDecimal price;

    private List<ProductCombo> productCombos;
}
