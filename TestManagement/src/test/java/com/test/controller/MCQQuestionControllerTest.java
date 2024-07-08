package com.test.controller;

import com.test.entities.MCQQuestion;
import com.test.service.MCQQuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MCQQuestionControllerTest {

    @Mock
    private MCQQuestionService questionService;

    @InjectMocks
    private MCQQuestionController questionController;

    @Test
    void testCreateQuestion() {
        MCQQuestion expectedQuestion = new MCQQuestion();
        expectedQuestion.setId(1L);
        expectedQuestion.setQuestion("Test Question");
        when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(expectedQuestion);
        ResponseEntity<MCQQuestion> actualQuestion = questionController.createQuestion(expectedQuestion);
        assertThat(actualQuestion.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actualQuestion.getBody()).isEqualTo(expectedQuestion);
    }

    @Test
    void testGetAllQuestions() {
        List<MCQQuestion> expectedQuestionsList = new ArrayList<>();
        expectedQuestionsList.add(new MCQQuestion());
        expectedQuestionsList.add(new MCQQuestion());
        when(questionService.getAllQuestions()).thenReturn(expectedQuestionsList);
        ResponseEntity<List<MCQQuestion>> actualQuestionList = questionController.getAllQuestions();
        assertThat(actualQuestionList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualQuestionList.getBody()).isEqualTo(expectedQuestionsList);
    }

    @Test
    void testGetQuestionById() {
        MCQQuestion expectedQuestion = new MCQQuestion();
        expectedQuestion.setId(1L);
        expectedQuestion.setQuestion("Test Question");
        when(questionService.getQuestionById(1L)).thenReturn(Optional.of(expectedQuestion));
        ResponseEntity<MCQQuestion> actualQuestion = questionController.getQuestionById(1L);
        assertThat(actualQuestion.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(actualQuestion.getBody()).isEqualTo(expectedQuestion);
    }

    @Test
    void testUpdateQuestionById() {
        MCQQuestion expectedQuestion = new MCQQuestion();
        expectedQuestion.setId(1L);
        expectedQuestion.setQuestion("Updated Question");
        when(questionService.updateQuestionById(eq(1L), any(MCQQuestion.class))).thenReturn(expectedQuestion);
        ResponseEntity<MCQQuestion> actualQuesiton = questionController.updateQuestionById(1L, expectedQuestion);
        assertThat(actualQuesiton.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualQuesiton.getBody()).isEqualTo(expectedQuestion);
    }

    @Test
    void testDeleteQuestionById() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Question deleted.");
        doNothing().when(questionService).deleteQuestionById(1L);
        ResponseEntity<String> actualResponse = questionController.deleteQuestionById(1L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(expectedResponse.getStatusCode());
        assertThat(actualResponse.getBody()).isEqualTo(expectedResponse.getBody());
    }
}
