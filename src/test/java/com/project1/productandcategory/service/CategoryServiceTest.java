package com.project1.productandcategory.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project1.productandcategory.exception.categoryNotFoundException;
import com.project1.productandcategory.exception.productNotFoundException;
import com.project1.productandcategory.model.category;
import com.project1.productandcategory.model.product;
import com.project1.productandcategory.repository.categoryRepo;
import com.project1.productandcategory.repository.productRepo;
import com.project1.productandcategory.service.categoryService;
import com.project1.productandcategory.service.productService;



public class CategoryServiceTest {

    @Mock
    private categoryRepo categoryRepo;

    @InjectMocks
    private categoryService categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        categoryService = new categoryService(categoryRepo);
    }

    @Test
    public void testGetAllCategories() {
        // Mock data
        List<category> categoryList = Arrays.asList(
                new category("Category 1", "Category Description 1"),
                new category("Category 2", "Category Description 2")
        );
        when(categoryRepo.findAll()).thenReturn(categoryList);

        // Call the service method
        List<category> result = categoryService.getAllCategories();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getCategory_Name());
        assertEquals("Category 2", result.get(1).getCategory_Name());
    }

    @Test
    public void testGetCategoryById() {
        // Mock data
        category category = new category("Category 1", "Category Description 1");
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(category));

        // Call the service method
        category result = categoryService.getCategoryById(1L);

        // Verify the result
        assertEquals("Category 1", result.getCategory_Name());
        assertEquals("Category Description 1", result.getC_description());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        // Mock data
        when(categoryRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and verify that it throws the expected exception
        assertThrows(categoryNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    public void testAddCategory() {
        // Mock data
        category category = new category("Category 1", "Category Description 1");
        when(categoryRepo.save(any(category.class))).thenReturn(category);

        // Call the service method
        category result = categoryService.addCategory(category);

        // Verify the result
        assertEquals("Category 1", result.getCategory_Name());
        assertEquals("Category Description 1", result.getC_description());
    }

    @Test
    public void testUpdateCategory() {
        // Mock data
        category existingCategory = new category( "Category 1", "Category Description 1");
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(existingCategory));

        // Call the service method
        categoryService.updateCategory(1L, new category("Updated Category", "Updated Description"));

        // Verify that the repository's save method was called
        verify(categoryRepo, times(1)).save(any(category.class));
    }

    @Test
    public void testUpdateCategory_NotFound() {
        // Mock data
        when(categoryRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and verify that it throws the expected exception
        assertThrows(categoryNotFoundException.class, () -> categoryService.updateCategory(1L, new category()));
    }

    @Test
    public void testDeleteCategory() {
        // Mock data
        category existingCategory = new category("Category 1", "Category Description 1");
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(existingCategory));

        // Call the service method
        categoryService.deleteCategory(1L);

        // Verify that the repository's delete method was called
        verify(categoryRepo, times(1)).delete(any(category.class));
    }

    @Test
    public void testDeleteCategory_NotFound() {
        // Mock data
        when(categoryRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and verify that it throws the expected exception
        assertThrows(categoryNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}
