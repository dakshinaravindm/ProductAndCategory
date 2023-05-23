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
import org.springframework.web.bind.annotation.RestController;

import com.project1.productandcategory.model.category;
import com.project1.productandcategory.repository.categoryRepo;
import com.project1.productandcategory.service.categoryService;

@RestController
@RequestMapping("/categories")
public class categoryController {
    @Autowired
    private categoryService categoryServices;

    @GetMapping
    public List<category> getAllCategories() {
        return categoryServices.getAllCategories();
    }
    @GetMapping("/{id}")
    public ResponseEntity<category> getCategoryById(@PathVariable("id") Long id) {
        Optional<category> category = Optional.of(categoryServices.getCategoryById(id));
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/addCategory")
    public ResponseEntity<category> addCategory(@RequestBody category category) {
        category savedCategory = categoryServices.addCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<category> updateCategory(@PathVariable("category_id") Long id, @RequestBody category updatedCategory) {
        Optional<category> existingCategory = Optional.of(categoryServices.getCategoryById(id));
        if (existingCategory.isPresent()) {
            category category = existingCategory.get();
            category.setCategory_Name(updatedCategory.getCategory_Name());
            category.setC_description(updatedCategory.getC_description());
            categoryServices.updateCategory(id,category);
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Optional<category> existingCategory = Optional.of(categoryServices.getCategoryById(id));
        if (existingCategory.isPresent()) {
            categoryServices.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

