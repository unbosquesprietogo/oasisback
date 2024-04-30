package com.oasisuniformes.inventario.service;

import com.oasisuniformes.inventario.entity.Product;
import com.oasisuniformes.inventario.entity.ProductDetail;
import com.oasisuniformes.inventario.model.request.PaginationResult;
import com.oasisuniformes.inventario.model.request.ProductRequest;
import com.oasisuniformes.inventario.repository.ProductDetailRepository;
import com.oasisuniformes.inventario.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<ProductRequest> getAllProducts() {

        List<Product> products = productRepository.findAll();
        List<ProductRequest> productRequests = new ArrayList<>();

        for (Product product: products){
            productRequests.add(new ProductRequest(product.getId(),product.getName(),
                    product.getPrice(),product.getCategory(),getProductsDetailById(product.getId())));
        }
        return productRequests;
    }

    public List<ProductRequest> getFirstNProducts(int n) {

        List<Product> products = productRepository.findAll();
        List<ProductRequest> productRequests = new ArrayList<>();

        int count = 0;

        for (Product product : products) {
            if (count >= n) {
                break; // Sale del bucle si ya se han obtenido los primeros n productos
            }

            productRequests.add(new ProductRequest(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory(),
                    getProductsDetailById(product.getId())
            ));

            count++;
        }

        return productRequests;
    }

    public PaginationResult getProductsByPagination(int start, int limit) {
        return  getByPagination(start, limit, productRepository.findAll());
    }

    public PaginationResult getProductsByNameAndPagination(String searchQuery, int start, int limit) {
       return getByPagination(start,limit,productRepository.findAllByNameContainingIgnoreCase(searchQuery));
    }

    public PaginationResult getByPagination(int start, int limit, List<Product> products) {


        List<ProductRequest> productRequests = new ArrayList<>();


        int startIndex = start;
        int endIndex = start + limit;


        for (int i = startIndex; i < endIndex && i < products.size(); i++) {
            Product product = products.get(i);
            productRequests.add(new ProductRequest(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory(),
                    getProductsDetailById(product.getId())
            ));
        }

        boolean isFirstPage = start == 0;
        boolean isLastPage = endIndex >= products.size();

        return new PaginationResult(productRequests, endIndex, isFirstPage, isLastPage);
    }


    public Optional<ProductRequest> getProductById(Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        ProductRequest productRequest = new ProductRequest(productOpt.get().getId(),productOpt.get().getName(),
                productOpt.get().getPrice(),productOpt.get().getCategory(),null);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            List<ProductDetail> details = productDetailRepository.findByProductId(product.getId());

            productRequest.setProductDetails(details);
            return Optional.of(productRequest);
        }
        return Optional.of(productRequest);
    }
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }


    public ProductRequest saveProductWithDetails(ProductRequest productRequest) {
        Product productSaved = productRepository.save(new Product(null,productRequest.getName(),
                productRequest.getPrice(),productRequest.getCategory()));
        for (ProductDetail productDetail : productRequest.getProductDetails()) {
            productDetail.setProduct(productSaved);
        }
        List<ProductDetail> productDetailListSaved = productDetailRepository.saveAll(productRequest.getProductDetails());
        return new ProductRequest(
                productSaved.getId(),productRequest.getName(),productRequest.getPrice(),
                productRequest.getCategory(),productDetailListSaved
        );
    }


    public Map<String, Object> saveAllProducts(List<ProductRequest> productRequestList) {
        Map<String, Object> result = new HashMap<>();
        List<ProductRequest> savedProducts = new ArrayList<>();
        List<ProductRequest> failedProducts = new ArrayList<>();

        for (ProductRequest productRequest : productRequestList) {
            try {
                Product productSaved = productRepository.save(new Product(null,productRequest.getName(),
                        productRequest.getPrice(),productRequest.getCategory()));

                for (ProductDetail productDetail : productRequest.getProductDetails()) {
                    productDetail.setProduct(productSaved);
                }

                List<ProductDetail> productDetailListSaved = productDetailRepository.saveAll(productRequest.getProductDetails());
                savedProducts.add(new ProductRequest(productSaved.getId(),productSaved.getName(),
                        productSaved.getPrice(),productSaved.getCategory(), productDetailListSaved));
            } catch (DataIntegrityViolationException e) {
                failedProducts.add(productRequest);
            } catch (Exception e) {
                failedProducts.add(productRequest);
            }
        }

        result.put("savedProducts", savedProducts);
        result.put("failedProducts", failedProducts);

        return result;
    }


    public Product updateProduct(Product product) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            throw new EntityNotFoundException("Product doesn't exists in database.");
        }
        return productRepository.save(product);
    }

    public ProductDetail updateProductDetail(ProductDetail productDetail) {
        if (productDetail.getId() == null || !productDetailRepository.existsById(productDetail.getId())) {
            throw new EntityNotFoundException("Product doesn't exists in database.");
        }
        return productDetailRepository.save(productDetail);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public List<ProductDetail> getProductsDetailById(int id){
        return productDetailRepository.findByProductId(id);
    }

    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepository.findAll();
    }

    public Optional<ProductDetail> getProductDetailById(Integer id) {
        return productDetailRepository.findById(id);
    }

    public ProductDetail saveProductDetail(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    public void deleteProductDetail(Integer id) {
        productDetailRepository.deleteById(id);
    }
}
