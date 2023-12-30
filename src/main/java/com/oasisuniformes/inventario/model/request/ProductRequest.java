package com.oasisuniformes.inventario.model.request;

import com.oasisuniformes.inventario.entity.Category;
import com.oasisuniformes.inventario.entity.Product;
import com.oasisuniformes.inventario.entity.ProductDetail;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ProductRequest {

    private Integer id;

    private String name;

    private BigDecimal price;

    private Category category;

    private List<ProductDetail> productDetails;
}
