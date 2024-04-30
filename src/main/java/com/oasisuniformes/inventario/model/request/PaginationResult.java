package com.oasisuniformes.inventario.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResult {
    private List<ProductRequest> products;
    private int endIndex;
    private boolean isFirstPage;
    private boolean isLastPage;

}
