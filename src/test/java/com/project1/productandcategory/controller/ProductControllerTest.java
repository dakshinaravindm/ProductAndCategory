package com.project1.productandcategory.controller;


import com.project1.productandcategory.model.category;
import com.project1.productandcategory.model.product;
import com.project1.productandcategory.service.categoryService;
import com.project1.productandcategory.service.productService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(productController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private categoryService categoryService;
    private productService productService;
    @BeforeEach
    public void setUp() {
        Mockito.reset(categoryService);
    }


    @Test
    public void testGetAllProducts() throws Exception {
        category category1 = new category( "Category 1", "Description 1");
        category category2 = new category( "Category 2", "Description 2");

        product product1 = new product("Product 1", 10.0, (long) 5, "Description 1", category1);
        product product2 = new product("Product 2", 15.0, (long) 10, "Description 2", category2);
        List<product> productList = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(productList.size()));

        verify(productService, times(1)).getAllProducts();
    }

//    @Test
//    public void testGetProductById() throws Exception {
//        product product = new product("Product 1", 10.0, 5);
//
//        when(productService.getProductById(1L)).thenReturn(product);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(product.getQuantity()));
//
//        verify(productService, times(1)).getProductById(1L);
//    }

    @Test
    public void testAddProduct() throws Exception {
        category category = new category("Category 1", "Description 1");
        product product = new product( "Product 1", 10.0, (long) 5, "Description 1", category);
        String jsonProduct = "{\"name\":\"Product 1\",\"price\":10.0,\"quantity\":5,\"description\":\"Description 1\",\"category\":{\"id\":1,\"name\":\"Category 1\",\"description\":\"Description 1\"}}";

        when(productService.addProduct(any(product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProduct))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getProduct_id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getProduct_name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(product.getQuantity()));

        verify(productService, times(1)).addProduct(any(product.class));
    }
 

}

