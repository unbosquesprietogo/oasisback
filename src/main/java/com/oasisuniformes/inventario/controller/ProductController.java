package com.oasisuniformes.inventario.controller;

import com.oasisuniformes.inventario.entity.Product;
import com.oasisuniformes.inventario.entity.ProductDetail;
import com.oasisuniformes.inventario.model.request.ProductRequest;
import com.oasisuniformes.inventario.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts() {

        try {
            List<ProductRequest> productList = productService.getAllProducts();
            return ResponseEntity.status(HttpStatus.OK).body(productList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting products: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRequest> getProductById(@PathVariable Integer id) {
        Optional<ProductRequest> product = productService.getProductById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductById(@PathVariable String name) {
        Optional<Product> product = productService.getProductByName(name);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest) {
        try {
            ProductRequest product = productService.saveProductWithDetails(productRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate product: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product: " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<Map<String, Object>> saveAllProducts(@RequestBody List<ProductRequest> productsRequest) {
        Map<String, Object> result = productService.saveAllProducts(productsRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        if (!id.equals(product.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/detail/{id}")
    public ResponseEntity<ProductDetail> updateProduct(@PathVariable Integer id, @RequestBody ProductDetail productDetail) {
        if (!id.equals(productDetail.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductDetail updatedProductDetail = productService.updateProductDetail(productDetail);
        return new ResponseEntity<>(updatedProductDetail, HttpStatus.OK);
    }
}
