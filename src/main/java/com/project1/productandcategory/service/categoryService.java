package com.project1.productandcategory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.productandcategory.exception.categoryNotFoundException;
import com.project1.productandcategory.model.category;
import com.project1.productandcategory.repository.categoryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class categoryService {
    @Autowired
    private categoryRepo categoryRepository;
    //test
    @Autowired
    public categoryService(categoryRepo categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public List<category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public category getCategoryById(Long
id) {
return categoryRepository.findById(id)
.orElseThrow(() -> new categoryNotFoundException("Category not found with id: " + id));
}
    public category addCategory(category category) {
        return categoryRepository.save(category);
    }

    public void updateCategory(Long id, category updatedCategory) {
        Optional<category> existingCategoryOptional = categoryRepository.findById(id);
        if (existingCategoryOptional.isPresent()) {
            category existingCategory = existingCategoryOptional.get();
            existingCategory.setCategory_Name(updatedCategory.getCategory_Name());
            existingCategory.setC_description(updatedCategory.getC_description());
            categoryRepository.save(existingCategory);
        } else {
            // Handle the case where the category with the specified ID is not found
            throw new categoryNotFoundException("Category not found");
        }
    }



    public void deleteCategory(Long id) {
        category category = getCategoryById(id);
        categoryRepository.delete(category);
    }}
