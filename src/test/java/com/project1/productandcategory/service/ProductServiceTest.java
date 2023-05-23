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

public class ProductServiceTest {

    @Mock
    private productRepo productRepo;

    @InjectMocks
    private productService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new productService(productRepo);
    }

    @Test
    public void testGetAllProducts() {
        // Mock data
        List<product> productList = Arrays.asList(
                new product("Product 1", 10.0, (long) 5, "Description 1", null),
                new product("Product 2", 15.0, (long) 8, "Description 2", null)
        );
        when(productRepo.findAll()).thenReturn(productList);

        // Call the service method
        List<product> result = productService.getAllProducts();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getProduct_name());
        assertEquals("Product 2", result.get(1).getProduct_name());
    }

    @Test
    public void testGetProductById() {
        // Mock data
        product product = new product("Product 1", 10.0, (long) 5, "Description 1", null);
        when(productRepo.findById(1L)).thenReturn(Optional.of(product));

        // Call the service method
        product result = productService.getProductById(1L);

        // Verify the result
        assertEquals("Product 1", result.getProduct_name());
        assertEquals(10.0, result.getPrice());
        assertEquals(5, result.getQuantity());
        assertEquals("Description 1", result.getDescription());
    }

    @Test
    public void testGetProductById_NotFound() {
        // Mock data
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and verify that it throws the expected exception
        assertThrows(productNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    public void testAddProduct() {
        // Mock data
        product product = new product("Product 1", 10.0, (long) 5, "Description 1", null);
        when(productRepo.save(any(product.class))).thenReturn(product);

        // Call the service method
        product result = productService.addProduct(product);

        // Verify the result
        assertEquals("Product 1", result.getProduct_name());
        assertEquals(10.0, result.getPrice());
        assertEquals(5, result.getQuantity());
        assertEquals("Description 1", result.getDescription());
    }

    @Test
    public void testUpdateProduct() {
        // Mock data
        product existingProduct = new product("Product 1", 10.0, (long) 5, "Description 1", null);
        when(productRepo.findById(1L)).thenReturn(Optional.of(existingProduct));

        // Call the service method
        productService.updateProduct(1L, new product("Updated Product", 20.0, (long) 8, "Updated Description", null));

        // Verify that the repository's save method was called
        verify(productRepo, times(1)).save(any(product.class));
    }

    @Test
    public void testUpdateProduct_NotFound() {
        // Mock data
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and verify that it throws the expected exception
        assertThrows(productNotFoundException.class, () -> productService.updateProduct(1L, new product()));
    }

    @Test
    public void testDeleteProduct() {
        // Mock data
        product existingProduct = new product( "Product 1", 10.0, (long) 5, "Description 1", null);
        when(productRepo.findById(1L)).thenReturn(Optional.of(existingProduct));

        // Call the service method
        productService.deleteProduct(1L);

        // Verify that the repository's delete method was called
        verify(productRepo, times(1)).delete(any(product.class));
    }

    @Test
    public void testDeleteProduct_NotFound() {
        // Mock data
        when(productRepo.findById(1L)).thenReturn(Optional.empty());

        // Call the service method and verify that it throws the expected exception
        assertThrows(productNotFoundException.class, () -> productService.deleteProduct(1L));
    }}
