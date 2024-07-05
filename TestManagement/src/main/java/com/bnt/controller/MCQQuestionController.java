package com.bnt.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bnt.entities.MCQQuestion;
import com.bnt.exception.MCQQuestionDeleteException;
import com.bnt.service.MCQQuestionService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/questions")
public class MCQQuestionController {

    @Autowired
    private MCQQuestionService questionService;

    @PostMapping
    public ResponseEntity<MCQQuestion> createQuestion(@RequestBody MCQQuestion question) {
        log.info("Request recieved in MCQQuestionController for creating question: {}", question.getQuestion());
        MCQQuestion createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }

    @PostMapping("/uploadBulkQuestions")
    public ResponseEntity<Map<String, List<Object>>> uploadBulkQuestions(@RequestParam("file") MultipartFile file) {
        log.info("Request recieved in MCQQuestionController for upload bulk questions.");
        Map<String, List<Object>> map=new LinkedHashMap<>();
        
            List<Object> sucssessList=new ArrayList<>();
            sucssessList.add("File uploaded and questions stored successfully.");
            map.put("Success Message", sucssessList);
            map.putAll(questionService.uploadBulkQuestions(file));
            
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
        return question.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MCQQuestion> updateQuestionById(@PathVariable Long id, @RequestBody MCQQuestion question) {
        log.info("Request recieved in MCQQuestionController for update question with id: {}", id);
        if (!questionService.getQuestionById(id).isPresent()) {
            MCQQuestion notFoundQuestion = new MCQQuestion();
            notFoundQuestion.setQuestion("Question with given id not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundQuestion);
        }
        question.setId(id);
        MCQQuestion updatedQuestion = questionService.updateQuestionById(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id) {
        log.info("Request recieved in MCQQuestionController for delete question with id: {}", id);
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
