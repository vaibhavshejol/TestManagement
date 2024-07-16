package com.test.service;

import com.test.entities.MCQQuestion;
import com.test.entities.Subcategory;
import com.test.exception.DataDeleteException;
import com.test.exception.DataNotFoundException;
import com.test.repository.MCQQuestionRepository;
import com.test.service.serviceimpl.MCQQuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MCQQuestionServiceImplTest {

    @Mock
    private MCQQuestionRepository mcqQuestionRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private SubcategoryService subcategoryService;

    @InjectMocks
    private MCQQuestionServiceImpl mcqQuestionService;

    private List<MCQQuestion> expectedQuestionsList;
    private MCQQuestion expectedQuestion;

    @BeforeEach
    void setUp() {
        expectedQuestionsList = new ArrayList<>();
        expectedQuestion = new MCQQuestion();
        expectedQuestion.setId(1L);
        expectedQuestion.setQuestion("What is Java?");
        expectedQuestion.setOptionOne("OOP");
        expectedQuestion.setOptionTwo("SOOP");
        expectedQuestion.setOptionThree("BOOP");
        expectedQuestion.setOptionFour("HOOP");
        expectedQuestion.setCorrectOption("OOP");
        expectedQuestion.setPositiveMark(4);
        expectedQuestion.setNegativeMark(1);
        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryId(1L);
        subcategory.setSubcategoryName("Core Java Basics");
        expectedQuestion.setSubcategory(subcategory);
        expectedQuestionsList.add(expectedQuestion);
    }

    @Test
    void testCreateQuestion() {
        when(mcqQuestionRepository.findQuestionIdByQuestionName(anyString())).thenReturn(null);
        when(mcqQuestionRepository.save(any(MCQQuestion.class))).thenReturn(expectedQuestion);
        MCQQuestion actualQuestion = mcqQuestionService.createQuestion(expectedQuestion);
        assertThat(actualQuestion).isNotNull();
        assertThat(actualQuestion.getId()).isEqualTo(1L);
        assertThat(actualQuestion.getQuestion()).isEqualTo("What is Java?");
        assertThat(actualQuestion.getSubcategory().getSubcategoryId()).isEqualTo(1L);
        verify(mcqQuestionRepository, times(1)).save(any(MCQQuestion.class));
    }

    @Test
    void testGetAllQuestions() {
        when(mcqQuestionRepository.findAll()).thenReturn(expectedQuestionsList);
        List<MCQQuestion> actualQuestionsList = mcqQuestionService.getAllQuestions();
        assertThat(actualQuestionsList).isNotNull().hasSize(1);
        assertThat(actualQuestionsList.get(0).getId()).isEqualTo(1L);
        assertThat(actualQuestionsList.get(0).getQuestion()).isEqualTo("What is Java?");
        verify(mcqQuestionRepository, times(1)).findAll();
    }

    @Test
    void testGetQuestionById() {
        when(mcqQuestionRepository.findById(1L)).thenReturn(Optional.of(expectedQuestion));
        Optional<MCQQuestion> actualQuestion = mcqQuestionService.getQuestionById(1L);
        assertThat(actualQuestion).isPresent();
        assertThat(actualQuestion.get().getId()).isEqualTo(1L);
        assertThat(actualQuestion.get().getQuestion()).isEqualTo("What is Java?");
        verify(mcqQuestionRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateQuestionById() {
        when(mcqQuestionRepository.findById(1L)).thenReturn(Optional.of(expectedQuestion));
        when(mcqQuestionRepository.save(any(MCQQuestion.class))).thenReturn(expectedQuestion);
        MCQQuestion updatedQuestion = mcqQuestionService.updateQuestionById(1L, expectedQuestion);

        assertThat(updatedQuestion).isNotNull();
        assertThat(updatedQuestion.getId()).isEqualTo(1L);
        assertThat(updatedQuestion.getQuestion()).isEqualTo("What is Java?");
        verify(mcqQuestionRepository, times(1)).findById(1L);
        verify(mcqQuestionRepository, times(1)).save(any(MCQQuestion.class));
    }

    @Test
    void testDeleteQuestionById() {
        when(mcqQuestionRepository.findById(1L)).thenReturn(Optional.of(expectedQuestion));
        mcqQuestionService.deleteQuestionById(1L);
        verify(mcqQuestionRepository, times(1)).findById(1L);
        verify(mcqQuestionRepository, times(1)).deleteById(1L);
    }

    ////////////////// Negative test cases ////////////////////////

    @Test
    void testCreateQuestion_Negative() {
        MCQQuestion invalidQuestion = new MCQQuestion();
        invalidQuestion.setQuestion(null); // Invalid state

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            mcqQuestionService.createQuestion(invalidQuestion);
        });
        assertThat(exception.getMessage()).isEqualTo("Provided mcq question object not contain proper data.");
    }

    @Test
    void testGetAllQuestions_Negative() {
        when(mcqQuestionRepository.findAll()).thenThrow(new RuntimeException("MCQ questions not present in database."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mcqQuestionService.getAllQuestions();
        });
        assertThat(exception.getMessage()).isEqualTo("MCQ questions not present in database.");
    }

    @Test
    void testGetQuestionById_Negative() {
        when(mcqQuestionRepository.findById(1L)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            mcqQuestionService.getQuestionById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("MCQQuestion with Id: 1 not present in database.");
    }

    @Test
    void testUpdateQuestionById_Negative() {
        MCQQuestion updatedQuestion = new MCQQuestion();
        updatedQuestion.setId(1L);
        updatedQuestion.setQuestion("Updated Question");

        when(mcqQuestionRepository.findById(1L)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> {
            mcqQuestionService.updateQuestionById(1L, updatedQuestion);
        });
        assertThat(exception.getMessage()).isEqualTo("MCQQuestion with Id: 1 not present in database.");
    }

    @Test
    void testDeleteQuestionById_Negative() {
        when(mcqQuestionRepository.findById(1L)).thenReturn(Optional.empty());

        DataDeleteException exception = assertThrows(DataDeleteException.class, () -> {
            mcqQuestionService.deleteQuestionById(1L);
        });
        assertThat(exception.getMessage()).isEqualTo("Failed to delete question with id: 1 MCQQuestion with Id: 1 not present in database.");
    }
}
