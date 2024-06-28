package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Category;
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
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (!categoryService.getCategoryById(id).isPresent()) {
            Category notFoundCategory = new Category();
            notFoundCategory.setCategoryName("Category with given id not found.");
            return notFoundCategory;
        }
        category.setCategoryId(id);
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable Long id) {
        if (!categoryService.getCategoryById(id).isPresent()) {
            return "Category with given id not found";
        }
        categoryService.deleteCategory(id);
        return "Category deleted.";
    }
}
