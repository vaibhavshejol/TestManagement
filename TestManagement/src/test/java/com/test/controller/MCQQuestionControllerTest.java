package com.test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.test.entities.MCQQuestion;
import com.test.service.MCQQuestionService;

@SpringBootTest
public class MCQQuestionControllerTest {
    
    @MockBean
    private MCQQuestionService questionService;

    
    @Test
    public void createQuestion() {
        
    }

    @Test
    public void getAllQuestions() {
       
    }

    @Test
    public void getQuestionById() {
        
    }

    @Test
    public void updateQuestion() {
        
    }

    @Test
    public void deleteQuestion() {
        
    }

}
