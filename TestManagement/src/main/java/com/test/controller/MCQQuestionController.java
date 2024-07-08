package com.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.entities.MCQQuestion;
import com.test.exception.DataDeleteException;
import com.test.service.MCQQuestionService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/questions")
public class MCQQuestionController {

    private MCQQuestionService questionService;

    public MCQQuestionController(MCQQuestionService questionService){
        this.questionService=questionService;
    }

    @PostMapping
    public ResponseEntity<MCQQuestion> createQuestion(@RequestBody MCQQuestion question) {
        log.info("Request recieved in MCQQuestionController for creating question: {}", question.getQuestion());
        MCQQuestion createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PostMapping("/uploadBulkQuestions")
    public ResponseEntity<Map<String, List<Object>>> uploadBulkQuestions(@RequestParam("file") MultipartFile file) {
        log.info("Request recieved in MCQQuestionController for upload bulk questions.");
        Map<String, List<Object>> map=questionService.uploadBulkQuestions(file);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping
    public ResponseEntity<List<MCQQuestion>> getAllQuestions() {
        log.info("Request recieved in MCQQuestionController for get all questions.");
        List<MCQQuestion> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MCQQuestion> getQuestionById(@PathVariable Long id) {
        log.info("Request recieved in MCQQuestionController for fetch question with id: {}", id);
        Optional<MCQQuestion> question = questionService.getQuestionById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(question.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MCQQuestion> updateQuestionById(@PathVariable Long id, @RequestBody MCQQuestion question) {
        log.info("Request recieved in MCQQuestionController for update question with id: {}", id);
        MCQQuestion updatedQuestion = questionService.updateQuestionById(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id) {
        log.info("Request recieved in MCQQuestionController for delete question with id: {}", id);
        questionService.deleteQuestionById(id);
        return ResponseEntity.ok("Question deleted.");
    }

    @ExceptionHandler(DataDeleteException.class)
    public ResponseEntity<String> handleQuestionDeleteException(DataDeleteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    
}
