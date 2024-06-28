package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entities.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    
}
