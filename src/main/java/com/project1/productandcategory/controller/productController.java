package com.project1.productandcategory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project1.productandcategory.model.category;
import com.project1.productandcategory.model.product;
import com.project1.productandcategory.repository.categoryRepo;
import com.project1.productandcategory.repository.productRepo;
import com.project1.productandcategory.service.productService;

@RestController
@RequestMapping("/products")
public class productController {
	@Autowired
	private productService productServices;
    @GetMapping
    public List<product> getAllProducts() {
        return productServices.getAllProducts();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<product> addProduct(@RequestBody product product) {
        product savedProduct = productServices.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<product> updateProduct(@PathVariable("product_id") Long id, @RequestBody product updatedProduct) {
        Optional<product> existingProduct = Optional.of(productServices.getProductById(id));
        if (existingProduct.isPresent()) {
            product product = existingProduct.get();
            product.setProduct_name(updatedProduct.getProduct_name());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setDescription(updatedProduct.getDescription());
            productServices.addProduct(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{productId}/price")
    public ResponseEntity<product> updateProductPrice(@PathVariable("productId") Long productId, @RequestParam("price") double newPrice) {
        product updatedProduct = productServices.updateProductPrice(productId, newPrice);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<product> updateProductQuantity(@PathVariable("productId") Long productId, @RequestParam("quantity") Long newQuantity) {
        product updatedProduct = productServices.updateProductQuantity(productId, newQuantity);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<product> existingProduct = Optional.of(productServices.getProductById(id));
        if (existingProduct.isPresent()) {
            productServices.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

