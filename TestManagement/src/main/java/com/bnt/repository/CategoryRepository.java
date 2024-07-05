package com.bnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bnt.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.categoryId FROM Category c WHERE c.categoryName = ?1")
    Long findCategoryIdByCategoryName(String categoryName);
}
