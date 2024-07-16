package com.test.controller;

import com.test.entities.MCQQuestion;
import com.test.exception.DataNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MCQQuestionControllerTest {

    @Mock
    private MCQQuestionService questionService;

    @InjectMocks
    private MCQQuestionController questionController;

    // Test for create Question
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

    // Negative test for create question
    @Test
    void testCreateQuestion_Negative() {
        MCQQuestion invalidQuestion = new MCQQuestion();
        invalidQuestion.setQuestion(null); // Invalid state

        when(questionService.createQuestion(any(MCQQuestion.class)))
            .thenThrow(new IllegalArgumentException("Question cannot be null"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionController.createQuestion(invalidQuestion);
        });

        assertThat(exception.getMessage()).isEqualTo("Question cannot be null");
    }

    // Test for getAllQuestion
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

    //Negative test for getAllQuestion
    @Test
    void testGetAllQuestions_Negative() {
        when(questionService.getAllQuestions()).thenThrow(new RuntimeException("There is no questions present in database."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionController.getAllQuestions();
        });

        assertThat(exception.getMessage()).isEqualTo("There is no questions present in database.");
    }

    // Test for get question by id
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

    // Negative test for get question by id
    @Test
    void testGetQuestionById_Negative() {
        when(questionService.getQuestionById(1L)).thenThrow(new DataNotFoundException("There is no question present with this id."));
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, ()-> {
            questionController.getQuestionById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("There is no question present with this id.");
    }

    // Test for update question by id
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

    // Negative test for update question by id
    @Test
    void testUpdateQuestionById_Negative() {
        MCQQuestion invalidQuestion = new MCQQuestion();
        invalidQuestion.setQuestion(null); // Invalid state

        when(questionService.updateQuestionById(eq(1L), any(MCQQuestion.class)))
            .thenThrow(new IllegalArgumentException("Invalid question data"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            questionController.updateQuestionById(1L, invalidQuestion);
        });

        assertThat(exception.getMessage()).isEqualTo("Invalid question data");
    }

    // Test for delete question by id
    @Test
    void testDeleteQuestionById() {
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Question deleted.");
        doNothing().when(questionService).deleteQuestionById(1L);
        ResponseEntity<String> actualResponse = questionController.deleteQuestionById(1L);
        assertThat(actualResponse.getStatusCode()).isEqualTo(expectedResponse.getStatusCode());
        assertThat(actualResponse.getBody()).isEqualTo(expectedResponse.getBody());
    }

    // Negative test for delete question by id    
    @Test
    void testDeleteQuestionById_Negative() {
        doThrow(new DataNotFoundException("Question not found")).when(questionService).deleteQuestionById(1L);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            questionController.deleteQuestionById(1L);
        });

        assertThat(exception.getMessage()).isEqualTo("Question not found");
    }
}
