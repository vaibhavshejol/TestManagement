package com.bnt.service;

import java.util.*;

import com.bnt.entities.Subcategory;

public interface SubcategoryService {

    public Long getSubcategoryIdBySubcategoryName(String categoryName);

    Subcategory createSubcategory(Subcategory subcategory);

    List<Subcategory> getAllSubcategory();

    Optional<Subcategory> getSubcategoryById(Long id);

    Subcategory updateSubcategoryById(Subcategory subcategory);

    void deleteSubcategoryById(Long id);
}
