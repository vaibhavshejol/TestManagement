// package com.test.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.test.entities.MCQQuestion;
// import com.test.entities.Subcategory;
// import com.test.repository.MCQQuestionRepository;

// @SpringBootTest
// public class MCQQuestionServiceImplTest {
    
//     @Mock
//     private MCQQuestionRepository repo;
    
//     @Mock
//     private MCQQuestionService questionService;

//     @Test
//     public void createQuestion() {
//         MCQQuestion expectedQuestion=new MCQQuestion();
//         Subcategory subcategory=new Subcategory();
//         expectedQuestion.setId(1L);
//         expectedQuestion.setSubcategory(subcategory);
//         expectedQuestion.setQuestion("What is spring boot?");

//         when(repo.save(any(MCQQuestion.class))).thenReturn(expectedQuestion);
//         assertEquals(1, expectedQuestion.getId());
//     }

//     @Test
//     public void getAllQuestions() {
//         MCQQuestion expectedQuestion=new MCQQuestion();
//         Subcategory subcategory=new Subcategory();
//         expectedQuestion.setId(1L);
//         expectedQuestion.setSubcategory(subcategory);
//         expectedQuestion.setQuestion("What is spring boot?");

//         MCQQuestion expectedQuestion1=new MCQQuestion();
//         Subcategory subcategory1=new Subcategory();
//         expectedQuestion1.setId(2L);
//         expectedQuestion.setSubcategory(subcategory1);
//         expectedQuestion1.setQuestion("What is hibernate?");

//         List<MCQQuestion> list=new ArrayList<MCQQuestion>();
//         list.add(expectedQuestion);
//         list.add(expectedQuestion1);

//         when(repo.findAll()).thenReturn(list);
//         assertEquals(2, list.size());
//     }

//     @Test
//     public void getQuestionById() {
//         MCQQuestion question=new MCQQuestion();
//         Subcategory subcategory=new Subcategory();
//         question.setId(1L);
//         question.setSubcategory(subcategory);
//         question.setQuestion("What is spring boot?");
//         Optional<MCQQuestion> expectedQuestion=Optional.of(question);
        

//         when(repo.findById(1L)).thenReturn(expectedQuestion);
//         assertEquals(1, expectedQuestion.get().getId());
//     }

//     @Test
//     public void updateQuestion() {
//         MCQQuestion expectedQuestion=new MCQQuestion();
//         Subcategory subcategory=new Subcategory();
//         expectedQuestion.setId(1L);
//         expectedQuestion.setSubcategory(subcategory);
//         expectedQuestion.setQuestion("What is spring boot?");

//         when(repo.save(any(MCQQuestion.class))).thenReturn(expectedQuestion);
//         assertEquals(1, expectedQuestion.getId());
//     }

// }
