package com.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.entities.Category;
import com.test.service.CategoryService;

@SpringBootTest
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category();
        category.setCategoryName("Test Category");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<?> responseEntity = categoryController.createCategory(category);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    public void testGetAllCategory() {
        Category category1 = new Category();
        category1.setCategoryName("Category 1");
        Category category2 = new Category();
        category2.setCategoryName("Category 2");

        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategory()).thenReturn(categories);

        ResponseEntity<List<Category>> responseEntity = categoryController.getAllCategory();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categories, responseEntity.getBody());
        verify(categoryService, times(1)).getAllCategory();
    }

    @Test
    public void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Test Category");

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<Category> responseEntity = categoryController.getCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(category, responseEntity.getBody());
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testGetCategoryById_NotFound() {
        Long categoryId = 1L;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        ResponseEntity<Category> responseEntity = categoryController.getCategoryById(categoryId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    public void testUpdateCategoryById() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setCategoryId(categoryId);
        updatedCategory.setCategoryName("Updated Test Category");

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(new Category()));
        when(categoryService.updateCategoryById(any(Category.class))).thenReturn(updatedCategory);

        ResponseEntity<Category> responseEntity = categoryController.updateCategoryById(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCategory, responseEntity.getBody());
        verify(categoryService, times(1)).updateCategoryById(any(Category.class));
    }

    @Test
    public void testUpdateCategoryById_NotFound() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setCategoryId(categoryId);
        updatedCategory.setCategoryName("Updated Test Category");

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        ResponseEntity<Category> responseEntity = categoryController.updateCategoryById(categoryId, updatedCategory);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(categoryService, times(0)).updateCategoryById(any(Category.class));
    }

    @Test
    public void testDeleteCategoryById() {
        Long categoryId = 1L;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(new Category()));

        ResponseEntity<String> responseEntity = categoryController.deleteCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category deleted.", responseEntity.getBody());
        verify(categoryService, times(1)).deleteCategoryById(categoryId);
    }

    @Test
    public void testDeleteCategoryById_NotFound() {
        Long categoryId = 1L;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());

        ResponseEntity<String> responseEntity = categoryController.deleteCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category with given id is not present to delete.", responseEntity.getBody());
        verify(categoryService, times(0)).deleteCategoryById(categoryId);
    }
}
