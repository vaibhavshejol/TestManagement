package com.bnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bnt.entities.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("SELECT s.subcategoryId FROM Subcategory s WHERE s.subcategoryName = ?1")
    Long findSubcategoryIdBySubcategoryName(String subcategoryName);
}