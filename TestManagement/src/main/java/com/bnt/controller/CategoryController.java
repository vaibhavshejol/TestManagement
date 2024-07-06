package com.bnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnt.entities.Category;
import com.bnt.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //API for create or save category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        log.info("Request recieved in category controller for creating category with name: {}", category.getCategoryName());
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    //API for get all category
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory() {
        log.info("Request recieved in category controller for fetching all categories");
        List<Category> categories = categoryService.getAllCategory();
        return ResponseEntity.ok(categories);
    }

    //API for get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        log.info("Request recieved in category controller for fetching category with id: {}", id);
        Optional<Category> category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(category.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategoryById(@PathVariable Long id, @RequestBody Category category) {
        log.info("Request received in category controller for updating category with id: {}", id);
        Category updatedCategory = categoryService.updateCategoryById(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
        log.info("Request received in category controller for deleting category with id: {}", id);
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category deleted.");
    }
}
