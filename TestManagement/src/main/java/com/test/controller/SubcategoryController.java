package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Subcategory;
import com.test.service.SubcategoryService;

import java.util.*;

@RestController
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;
    
    @PostMapping("/subcategory")
    public Subcategory createSubcategory(@RequestBody Subcategory subcategory) {
        return subcategoryService.createSubcategory(subcategory);
    }

    @GetMapping("/subcategory")
    public List<Subcategory> getAllSubcategory() {
       
        return subcategoryService.getAllSubcategory();
    }

    @GetMapping("/subcategory/{id}")
    public Optional<Subcategory> getSubcategoryById(@PathVariable Long id) {
        return subcategoryService.getSubcategoryById(id);
    }

    @PutMapping("/subcategory/{id}")
    public Subcategory updateSubcategory(@PathVariable Long id, @RequestBody Subcategory subcategory) {
        if (!subcategoryService.getSubcategoryById(id).isPresent()) {
            Subcategory notFoundSubcategory = new Subcategory();
            notFoundSubcategory.setSubcategoryName("Subcategory with given id not found.");
            return notFoundSubcategory;
        }
        subcategory.setSubcategoryId(id);
        return subcategoryService.updateSubcategory(subcategory);
    }

    @DeleteMapping("/subcategory/{id}")
    public String deleteSubcategory(@PathVariable Long id) {
        if (!subcategoryService.getSubcategoryById(id).isPresent()) {
            return "Subcategory with given id not found";
        }
        subcategoryService.deleteSubcategory(id);
        return "Subcategory deleted.";
    }
}
