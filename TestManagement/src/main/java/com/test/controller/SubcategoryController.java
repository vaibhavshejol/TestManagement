package com.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Subcategory;
import com.test.exception.DataDeleteException;
import com.test.service.SubcategoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {

    private SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService){
        this.subcategoryService=subcategoryService;
    }
    
    @PostMapping
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) {
        log.info("Request recieved for creating subcategory: {}", subcategory.getSubcategoryName());
        Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
    }

    @GetMapping
    public ResponseEntity<List<Subcategory>> getAllSubcategory() {
        log.info("Request recieved for fetch all subcategory.");
        List<Subcategory> subcategories = subcategoryService.getAllSubcategory();
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable Long id) {
        log.info("Request recieved for fetch subcategory with id: {}", id);
        Optional<Subcategory> subcategory = subcategoryService.getSubcategoryById(id);
        return ResponseEntity.ok(subcategory.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategory> updateSubcategoryById(@PathVariable Long id, @RequestBody Subcategory subcategory) {
        log.info("Request recieved for update subcategory with id: {}", id);
        Subcategory updatedSubcategory = subcategoryService.updateSubcategoryById(id, subcategory);
        return ResponseEntity.ok(updatedSubcategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubcategoryById(@PathVariable Long id) {
        log.info("Request recieved for delete subcategory with id: {}", id);
        try {
            if (!subcategoryService.getSubcategoryById(id).isPresent()) {
                return ResponseEntity.ok("Subcategory with given id is not present to delete.");
            }
            subcategoryService.deleteSubcategoryById(id);
            return ResponseEntity.ok("Subcategory deleted.");
        } catch (DataDeleteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @ExceptionHandler(DataDeleteException.class)
    public ResponseEntity<String> handleSubcategoryDeleteException(DataDeleteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
