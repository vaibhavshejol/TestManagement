package com.test.service;

import com.test.entities.MCQQuestion;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

public interface MCQQuestionService {

    MCQQuestion createQuestion(MCQQuestion question);

    List<MCQQuestion> getAllQuestions();

    Optional<MCQQuestion> getQuestionById(Long id);

    MCQQuestion updateQuestionById(MCQQuestion question);

    void deleteQuestionById(Long id);

    Map<String, List<Object>> uploadBulkQuestions(MultipartFile file);
    
}
