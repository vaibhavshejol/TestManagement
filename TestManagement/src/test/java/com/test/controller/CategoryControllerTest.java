package com.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.entities.Category;
import com.test.service.CategoryService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category expectedCategory;

    @BeforeEach
    void setUp() {
        expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setCategoryName("Java");
        expectedCategory.setCategoryDescription("Core Java Category");
    }

    // Test for createCategory API
    @Test
    void testCreateCategory() {
        when(categoryService.createCategory(any(Category.class))).thenReturn(expectedCategory);
        ResponseEntity<Category> actualCategory = categoryController.createCategory(expectedCategory);
        assertThat(actualCategory.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actualCategory.getBody()).isEqualTo(expectedCategory);
    }

    // Test for getAllCategory API
    @Test
    void testGetAllCategory() {
        List<Category> expectedCategoryList = new ArrayList<>();
        expectedCategoryList.add(expectedCategory);
        when(categoryService.getAllCategory()).thenReturn(expectedCategoryList);
        ResponseEntity<List<Category>> actualCategoryList = categoryController.getAllCategory();
        assertThat(actualCategoryList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualCategoryList.getBody()).isEqualTo(expectedCategoryList);
    }

    // Test for getCategoryById API
    @Test
    void testGetCategoryById() {
        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(expectedCategory));
        ResponseEntity<Category> actualCategory = categoryController.getCategoryById(1L);
        assertThat(actualCategory.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(actualCategory.getBody()).isEqualTo(expectedCategory);
    }

    // Test for updateCategoryById API
    @Test
    void testUpdateCategoryById() {
        when(categoryService.updateCategoryById(eq(1L), any(Category.class))).thenReturn(expectedCategory);
        ResponseEntity<Category> actualCategory = categoryController.updateCategoryById(1L, expectedCategory);
        assertThat(actualCategory.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualCategory.getBody()).isEqualTo(expectedCategory);
    }

    // Test for deleteCategoryById API
    @Test
    void testDeleteCategoryById() {
        doNothing().when(categoryService).deleteCategoryById(1L);
        ResponseEntity<String> response = categoryController.deleteCategoryById(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Category deleted.");
    }
}
