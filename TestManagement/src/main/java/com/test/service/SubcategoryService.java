package com.test.service;

import com.test.entities.Subcategory;

import java.util.*;

public interface SubcategoryService {

    public Long getSubcategoryIdBySubcategoryName(String categoryName);

    Subcategory createSubcategory(Subcategory subcategory);

    List<Subcategory> getAllSubcategory();

    Optional<Subcategory> getSubcategoryById(Long id);

    Subcategory updateSubcategoryById(Subcategory subcategory);

    void deleteSubcategoryById(Long id);
}
