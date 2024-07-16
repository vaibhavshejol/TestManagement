package com.test.service;

import com.test.entities.Category;
import com.test.exception.DataDeleteException;
import com.test.exception.DataNotFoundException;
import com.test.exception.IllegalArgumentException;
import com.test.repository.CategoryRepository;
import com.test.service.serviceimpl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void testCreateCategory() {
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setCategoryName("Java Category");
        expectedCategory.setCategoryDescription("Java Category Description.");
        when(categoryRepository.findCategoryIdByCategoryName("Java Category")).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(expectedCategory);
        Category createdCategory = categoryService.createCategory(expectedCategory);
        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getCategoryId()).isEqualTo(1L);
        assertThat(createdCategory.getCategoryName()).isEqualTo("Java Category");
        assertThat(createdCategory.getCategoryDescription()).isEqualTo("Java Category Description.");
    }

    @Test
    void testGetAllCategory() {
        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1L);
        category1.setCategoryName("Java Category");
        category1.setCategoryDescription("Java Category Description");
        categoryList.add(category1);
        Category category2 = new Category();
        category2.setCategoryId(2L);
        category2.setCategoryName("SQL Category");
        category2.setCategoryDescription("SQL Category Description");
        categoryList.add(category2);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        List<Category> actualCategories = categoryService.getAllCategory();
        assertThat(actualCategories).isNotEmpty().hasSize(2);
        assertThat(actualCategories.get(0)).isEqualTo(category1);
        assertThat(actualCategories.get(1)).isEqualTo(category2);
    }

    @Test
    void testGetCategoryById() {
        Category expectedCategory = new Category();
        expectedCategory.setCategoryId(1L);
        expectedCategory.setCategoryName("Java Category");
        expectedCategory.setCategoryDescription("Java Category Description.");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(expectedCategory));
        Optional<Category> fetchedCategory = categoryService.getCategoryById(1L);
        assertThat(fetchedCategory).isPresent();
        Category actualCategory = fetchedCategory.get();
        assertThat(actualCategory.getCategoryId()).isEqualTo(1L);
        assertThat(actualCategory.getCategoryName()).isEqualTo("Java Category");
        assertThat(actualCategory.getCategoryDescription()).isEqualTo("Java Category Description.");
    }

    @Test
    void testUpdateCategoryById() {
        Category existingCategory = new Category();
        existingCategory.setCategoryId(1L);
        existingCategory.setCategoryName("Existing Category");
        existingCategory.setCategoryDescription("Existing Description");

        Category updatedCategory = new Category();
        updatedCategory.setCategoryId(1L);
        updatedCategory.setCategoryName("Updated Category");
        updatedCategory.setCategoryDescription("Updated Description");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category actualCategory = categoryService.updateCategoryById(1L, updatedCategory);

        assertThat(actualCategory).isNotNull();
        assertThat(actualCategory.getCategoryId()).isEqualTo(1L);
        assertThat(actualCategory.getCategoryName()).isEqualTo("Updated Category");
        assertThat(actualCategory.getCategoryDescription()).isEqualTo("Updated Description");
    }

    @Test
    void testDeleteCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));
        categoryService.deleteCategoryById(1L);
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    /////////////////////////// Negative Test Cases ////////////////////////////////////

    @Test
    void testCreateCategory_Negative() {
        Category invalidCategory = new Category();
        invalidCategory.setCategoryName(null);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(invalidCategory);
        });
        assertThat(exception.getMessage()).isEqualTo("Provided category object not contain proper data.");

    }

    @Test
    void testGetAllCategory_Negative() {
        when(categoryRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.getAllCategory();
        });

        assertThat(exception.getMessage()).isEqualTo("Database error");
    }

    @Test
    void testGetCategoryById_Negative() {
        when(categoryRepository.findById(1L)).thenThrow(new DataNotFoundException("Category with this id not present."));
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, ()->{
            categoryService.getCategoryById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("Category with this id not present.");
    }

    @Test
    void testUpdateCategoryById_Negative() {
        Category updatedCategory = new Category();
        updatedCategory.setCategoryId(1L);
        updatedCategory.setCategoryName("Updated Category");
        updatedCategory.setCategoryDescription("Updated Description");

        when(categoryRepository.findById(1L)).thenThrow(new DataNotFoundException("Category not found."));
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, ()->{
            categoryService.updateCategoryById(1L, updatedCategory);
        });
        assertThat(exception.getMessage()).isEqualTo("Category not found.");
    }

    @Test
    void testDeleteCategoryById_Negative() {   
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());   
        assertThrows(DataDeleteException.class, () -> {
            categoryService.deleteCategoryById(1L);
        });
    }
}
