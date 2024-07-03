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

import com.test.entities.Subcategory;
import com.test.exception.SubcategoryDeleteException;
import com.test.service.SubcategoryService;

import java.util.*;

@RestController
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;
    
    @PostMapping("/subcategory")
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory) {
        Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubcategory);
    }

    @GetMapping("/subcategory")
    public ResponseEntity<List<Subcategory>> getAllSubcategory() {
        List<Subcategory> subcategories = subcategoryService.getAllSubcategory();
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/subcategory/{id}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable Long id) {
        Optional<Subcategory> subcategory = subcategoryService.getSubcategoryById(id);
        return subcategory.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/subcategory/{id}")
    public ResponseEntity<Subcategory> updateSubcategoryById(@PathVariable Long id, @RequestBody Subcategory subcategory) {
        if (!subcategoryService.getSubcategoryById(id).isPresent()) {
            Subcategory notFoundSubcategory = new Subcategory();
            notFoundSubcategory.setSubcategoryName("Subcategory with given id not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundSubcategory);
        }
        subcategory.setSubcategoryId(id);
        Subcategory updatedSubcategory = subcategoryService.updateSubcategoryById(subcategory);
        return ResponseEntity.ok(updatedSubcategory);
    }

    // @DeleteMapping("/subcategory/{id}")
    // public String deleteSubcategoryById(@PathVariable Long id) {
    //     if (!subcategoryService.getSubcategoryById(id).isPresent()) {
    //         return "Subcategory with given id not found";
    //     }
    //     subcategoryService.deleteSubcategoryById(id);
    //     return "Subcategory deleted.";
    // }

    @DeleteMapping("/subcategory/{id}")
    public ResponseEntity<String> deleteSubcategoryById(@PathVariable Long id) {
        try {
            if (!subcategoryService.getSubcategoryById(id).isPresent()) {
                return ResponseEntity.ok("Subcategory with given id is not present to delete.");
            }
            subcategoryService.deleteSubcategoryById(id);
            return ResponseEntity.ok("Subcategory deleted.");
        } catch (SubcategoryDeleteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @ExceptionHandler(SubcategoryDeleteException.class)
    public ResponseEntity<String> handleSubcategoryDeleteException(SubcategoryDeleteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
