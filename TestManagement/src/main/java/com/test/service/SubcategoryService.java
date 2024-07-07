package com.test.service;

import java.util.*;

import com.test.entities.Subcategory;

public interface SubcategoryService {

    public Long getSubcategoryIdBySubcategoryName(String categoryName);

    Subcategory createSubcategory(Subcategory subcategory);

    List<Subcategory> getAllSubcategory();

    Optional<Subcategory> getSubcategoryById(Long id);

    Subcategory updateSubcategoryById(Long id, Subcategory subcategory);

    void deleteSubcategoryById(Long id);
}
