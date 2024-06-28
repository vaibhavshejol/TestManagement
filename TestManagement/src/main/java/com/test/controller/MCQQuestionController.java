package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.MCQQuestion;
import com.test.exception.MCQQuestionDeleteException;
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
    public MCQQuestion updateQuestionById(@PathVariable Long id, @RequestBody MCQQuestion question) {
        if (!questionService.getQuestionById(id).isPresent()) {
            MCQQuestion notFoundQuestion = new MCQQuestion();
            notFoundQuestion.setQuestion("Question with given id not found.");
            return notFoundQuestion;
        }
        question.setId(id);
        return questionService.updateQuestionById(question);
    }

    // @DeleteMapping("/questions/{id}")
    // public String deleteQuestionById(@PathVariable Long id) {
    //     if (!questionService.getQuestionById(id).isPresent()) {
    //         return "Question with given id not found";
    //     }
    //     questionService.deleteQuestionById(id);
    //     return "Question deleted.";
    // }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id) {
        try {
            if (!questionService.getQuestionById(id).isPresent()) {
                return ResponseEntity.ok("Question with given id is not present to delete.");
            }
            questionService.deleteQuestionById(id);
            return ResponseEntity.ok("Question deleted.");
        } catch (MCQQuestionDeleteException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @ExceptionHandler(MCQQuestionDeleteException.class)
    public ResponseEntity<String> handleQuestionDeleteException(MCQQuestionDeleteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    
}
