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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.entities.MCQQuestion;
import com.test.exception.MCQQuestionDeleteException;
import com.test.service.MCQQuestionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
public class MCQQuestionController {
    
     private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private MCQQuestionService questionService;

    @PostMapping("/questions")
    public ResponseEntity<?> createQuestion(@RequestBody MCQQuestion question) {
        logger.info("Request recieved for creating question: {}", question.getQuestion());
        try{
            MCQQuestion createdQuestion = questionService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/uploadBulkQuestions")
    public ResponseEntity<Map<String, List<Object>>> uploadBulkQuestions(@RequestParam("file") MultipartFile file) {
        logger.info("Request recieved for upload bulk questions.");
        Map<String, List<Object>> map=new LinkedHashMap<>();
        try {
            if(!file.getOriginalFilename().endsWith(".xlsx")){
                List<Object> errorList=new ArrayList<>();
                errorList.add("Sorry... File you provided is not a type of \".xlsx\" extension.");
                map.put("Error message", errorList);
                return ResponseEntity.badRequest().body(map);
            }
            List<Object> sucssessList=new ArrayList<>();
            sucssessList.add("File uploaded and questions stored successfully.");
            map.put("Success Message", sucssessList);
            map.putAll(questionService.uploadBulkQuestions(file));;
            
            return ResponseEntity.ok().body(map);
        } catch (Exception ex) {
            List<Object> exceptionList=new ArrayList<>();
                exceptionList.add("Failed to process Excel file: "+ ex.getMessage());
                map.put("Error message", exceptionList);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<List<MCQQuestion>> getAllQuestions() {
        logger.info("Request recieved for get all questions.");
        List<MCQQuestion> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<MCQQuestion> getQuestionById(@PathVariable Long id) {
        logger.info("Request recieved for fetch question with id: {}", id);
        Optional<MCQQuestion> question = questionService.getQuestionById(id);
        return question.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<MCQQuestion> updateQuestionById(@PathVariable Long id, @RequestBody MCQQuestion question) {
        logger.info("Request recieved for update question with id: {}", id);
        if (!questionService.getQuestionById(id).isPresent()) {
            MCQQuestion notFoundQuestion = new MCQQuestion();
            notFoundQuestion.setQuestion("Question with given id not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundQuestion);
        }
        question.setId(id);
        MCQQuestion updatedQuestion = questionService.updateQuestionById(question);
        return ResponseEntity.ok(updatedQuestion);
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
        logger.info("Request recieved for delete question with id: {}", id);
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
