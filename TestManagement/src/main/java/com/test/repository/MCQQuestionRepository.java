package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.entities.MCQQuestion;

public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Long> {
    
}
