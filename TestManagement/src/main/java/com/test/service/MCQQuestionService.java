package com.test.service;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import com.test.entities.MCQQuestion;

public interface MCQQuestionService {

    MCQQuestion createQuestion(MCQQuestion question);

    List<MCQQuestion> getAllQuestions();

    Optional<MCQQuestion> getQuestionById(Long id);

    MCQQuestion updateQuestionById(Long id, MCQQuestion question);

    void deleteQuestionById(Long id);

    Map<String, List<Object>> uploadBulkQuestions(MultipartFile file);
    
}
