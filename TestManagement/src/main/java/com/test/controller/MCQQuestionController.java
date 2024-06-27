package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.MCQQuestion;
import com.test.service.MCQQuestionService;

import java.util.*;

@RestController
public class MCQQuestionController {
    

    @Autowired
    private MCQQuestionService questionService;

    @PostMapping("/questions")
    public MCQQuestion createQuestion(@RequestBody MCQQuestion question) {
        return questionService.createQuestion(question);
    }

    @GetMapping("/questions")
    public List<MCQQuestion> getAllQuestions() {
       
        return questionService.getAllQuestions();
    }

    @GetMapping("/questions/{id}")
    public Optional<MCQQuestion> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PutMapping("/questions/{id}")
    public MCQQuestion updateQuestion(@PathVariable Long id, @RequestBody MCQQuestion question) {
        if (!questionService.getQuestionById(id).isPresent()) {
            MCQQuestion notFoundQuestion = new MCQQuestion();
            notFoundQuestion.setQuestion("Question with given id not found.");
            return notFoundQuestion;
        }
        question.setId(id); // Ensure the correct ID is set
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        if (!questionService.getQuestionById(id).isPresent()) {
            return "Question with given id not found";
        }
        questionService.deleteQuestion(id);
        return "Question deleted.";
    }

    
}
