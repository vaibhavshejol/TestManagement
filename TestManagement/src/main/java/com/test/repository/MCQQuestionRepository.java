package com.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.test.entities.MCQQuestion;

public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, Long> {

    @Query("SELECT q.id FROM MCQQuestion q WHERE q.question = ?1")
    Long findQuestionIdByQuestionName(String question);
    
}
