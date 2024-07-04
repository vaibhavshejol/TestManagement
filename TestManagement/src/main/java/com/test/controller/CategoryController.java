package com.test.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Category;
import com.test.exception.CategoryDeleteException;
import com.test.service.CategoryService;

import org.slf4j.Logger;

import java.util.*;

@RestController
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        logger.info("Request recieved for creating category: {}", category.getCategoryName());
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategory() {
        logger.info("Request recieved for fetching all categories");
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        logger.info("Request recieved for fetching category with id: {}", id);
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

// @GetMapping("/category/{id}")
// public Optional<Category> getCategoryById(@PathVariable Long id) {
//     logger.info("Fetching category with id: {}", id);
//     return categoryService.getCategoryById(id);
// }

@PutMapping("/category/{id}")
public ResponseEntity<Category> updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
    logger.info("Request received for updating category with id: {}", id);
    if (!categoryService.getCategoryById(id).isPresent()) {
        Category notFoundCategory = new Category();
        notFoundCategory.setCategoryName("Category with given id not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundCategory);
    }
    category.setCategoryId(id);
    Category updatedCategory = categoryService.updateCategoryById(category);
    return ResponseEntity.ok(updatedCategory);
}

@DeleteMapping("/category/{id}")
public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
    logger.info("Request received for deleting category with id: {}", id);
    try {
        if (!categoryService.getCategoryById(id).isPresent()) {
            return ResponseEntity.ok("Category with given id is not present to delete.");
        }
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category deleted.");
    } catch (CategoryDeleteException ex) {
        logger.error("Failed to delete category with id: {}", id, ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}

}
