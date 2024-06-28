package com.test.controller;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

// import com.test.entities.MCQQuestion;
// import com.test.service.MCQQuestionService;

@SpringBootTest
public class MCQQuestionControllerTest {
    
    // @Mock
    // private MCQQuestionService questionService;

    // @Mock
    // private MCQQuestionController questionController;

    
    // @Test
    // public void createQuestion() {
    //     MCQQuestion expectedQuestion=new MCQQuestion();
    //     expectedQuestion.setId(1L);
    //     expectedQuestion.setCategory("SpringBoot");
    //     expectedQuestion.setQuestion("What is spring boot?");

    //     when(questionService.createQuestion(any(MCQQuestion.class))).thenReturn(expectedQuestion);
    //     assertEquals(1, expectedQuestion.getId());
    // }

    // @Test
    // public void getAllQuestions() {
    //     MCQQuestion expectedQuestion=new MCQQuestion();
    //     expectedQuestion.setId(1L);
    //     expectedQuestion.setCategory("SpringBoot");
    //     expectedQuestion.setQuestion("What is spring boot?");

    //     MCQQuestion expectedQuestion1=new MCQQuestion();
    //     expectedQuestion1.setId(2L);
    //     expectedQuestion1.setCategory("Hibernate");
    //     expectedQuestion1.setQuestion("What is hibernate?");

    //     List<MCQQuestion> list=new ArrayList<MCQQuestion>();
    //     list.add(expectedQuestion);
    //     list.add(expectedQuestion1);

    //     when(questionService.getAllQuestions()).thenReturn(list);
    //     assertEquals(2, list.size());
    // }

    // @Test
    // public void getQuestionById() {
    //     MCQQuestion question=new MCQQuestion();
    //     question.setId(1L);
    //     question.setCategory("SpringBoot");
    //     question.setQuestion("What is spring boot?");
    //     Optional<MCQQuestion> expectedQuestion=Optional.of(question);
        

    //     when(questionService.getQuestionById(1L)).thenReturn(expectedQuestion);
    //     assertEquals(1, expectedQuestion.get().getId());
    // }

    // @Test
    // public void updateQuestion() {
    //     MCQQuestion expectedQuestion=new MCQQuestion();
    //     expectedQuestion.setId(1L);
    //     expectedQuestion.setCategory("SpringBoot");
    //     expectedQuestion.setQuestion("What is spring boot?");

    //     when(questionService.updateQuestion(any(MCQQuestion.class))).thenReturn(expectedQuestion);
    //     assertEquals(1, expectedQuestion.getId());
    // }

    // @Test
    // public void deleteQuestion() {

    //     when(questionController.deleteQuestion(1L)).thenReturn("Question deleted.");

    //     String result=questionController.deleteQuestion(1L);
    //     assertEquals("Question deleted.", result);
    // }

}
