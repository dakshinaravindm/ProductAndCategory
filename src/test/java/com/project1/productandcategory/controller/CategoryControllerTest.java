package com.project1.productandcategory.controller;
import com.project1.productandcategory.model.category;
import com.project1.productandcategory.service.categoryService;
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
@WebMvcTest(categoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private categoryService categoryService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(categoryService);
    }

    @Test
    public void testGetAllCategories() throws Exception {
        category category1 = new category("Category 1", "Description 1");
        category category2 = new category("Category 2", "Description 2");
        List<category> categoryList = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categoryList);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(categoryList.size()));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        category category = new category("Category 1", "Description 1");

        when(categoryService.getCategoryById(1L)).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category.getCategory_Name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(category.getC_description()));

        verify(categoryService, times(1)).getCategoryById(1L);
    }

    @Test
    public void testAddCategory() throws Exception {
        category category = new category("Category 1", "Description 1");
        String jsonCategory = "{\"name\":\"Category 1\",\"description\":\"Description 1\"}";

        when(categoryService.addCategory(any(category.class))).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCategory))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category.getCategory_Name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(category.getC_description()));

        verify(categoryService, times(1)).addCategory(any(category.class));
    }

   

}

