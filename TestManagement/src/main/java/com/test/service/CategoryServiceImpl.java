package com.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Category;
import com.test.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repo;

    @Override
    public Category createCategory(Category category) {
        return repo.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return repo.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Category updateCategoryById(Category category) {
        return repo.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        repo.deleteById(id);
    }
    
}
