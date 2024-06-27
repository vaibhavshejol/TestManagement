package com.test.service;

import com.test.entities.MCQQuestion;

import java.util.*;

public interface MCQQuestionService {

    MCQQuestion createQuestion(MCQQuestion question);

    List<MCQQuestion> getAllQuestions();

    Optional<MCQQuestion> getQuestionById(Long id);

    MCQQuestion updateQuestion(MCQQuestion question);

    void deleteQuestion(Long id);
    
}
