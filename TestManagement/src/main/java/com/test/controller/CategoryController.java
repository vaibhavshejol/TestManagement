package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Category;
import com.test.exception.CategoryDeleteException;
import com.test.service.CategoryService;

import java.util.*;

@RestController
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/category")
    public List<Category> getAllCategory() {
       
        return categoryService.getAllCategory();
    }

    @GetMapping("/category/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/category/{id}")
    public Category updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
        if (!categoryService.getCategoryById(id).isPresent()) {
            Category notFoundCategory = new Category();
            notFoundCategory.setCategoryName("Category with given id not found.");
            return notFoundCategory;
        }
        category.setCategoryId(id);
        return categoryService.updateCategoryById(category);
    }

    // @DeleteMapping("/category/{id}")
    // public String deleteCategoryById(@PathVariable Long id) {
    //     if (!categoryService.getCategoryById(id).isPresent()) {
    //         return "Category with given id not found";
    //     }
    //     categoryService.deleteCategoryById(id);
    //     return "Category deleted.";
    // }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        try {
            if (!categoryService.getCategoryById(id).isPresent()) {
                 return ResponseEntity.ok("Category with given id is not present to delete.");
            }
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok("Category deleted.");
        } catch (CategoryDeleteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @ExceptionHandler(CategoryDeleteException.class)
    public ResponseEntity<String> handleCategoryDeleteException(CategoryDeleteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
