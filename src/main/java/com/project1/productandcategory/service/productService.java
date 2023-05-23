package com.project1.productandcategory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.productandcategory.exception.productNotFoundException;
import com.project1.productandcategory.model.product;
import com.project1.productandcategory.repository.*;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
@Service
public class productService {
    @Autowired
    private productRepo productRepo;
    //test
    @Autowired
    public productService(productRepo productRepository) {
        this.productRepo = productRepository;
    }
    public List<product> getAllProducts() {
        return productRepo.findAll();
    }

    public product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new productNotFoundException("Product not found with id: " + id));
    }

    public product addProduct(product product) {
        return productRepo.save(product);
    }

    public void updateProduct(Long id, product updatedProduct) {
        product product = getProductById(id);
        product.setProduct_name(updatedProduct.getProduct_name());
        product.setPrice(updatedProduct.getPrice());
        product.setQuantity(updatedProduct.getQuantity());
        product.setDescription(updatedProduct.getDescription());
        productRepo.save(product);
    }
    @Transactional
    public product updateProductPrice(Long productId, double newPrice) {
        Optional<product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            product product = optionalProduct.get();
            product.setPrice(newPrice);
            return productRepo.save(product);
        }
        return null;
    }
    @Transactional
    public product updateProductQuantity(Long productId, Long newQuantity) {
        Optional<product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isPresent()) {
            product product = optionalProduct.get();
            product.setQuantity(newQuantity);
            return productRepo.save(product);
        }
        return null;
    }

    

    public void deleteProduct(Long id) {
        product product = getProductById(id);
        productRepo.delete(product);
    }
}
