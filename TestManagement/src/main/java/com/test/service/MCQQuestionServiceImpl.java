package com.test.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.MCQQuestion;
import com.test.exception.MCQQuestionDeleteException;
import com.test.repository.MCQQuestionRepository;

@Service
public class MCQQuestionServiceImpl implements MCQQuestionService {

    @Autowired
    private MCQQuestionRepository repo;

    @Override
    public MCQQuestion createQuestion(MCQQuestion question) {
        return repo.save(question);
    }

    @Override
    public List<MCQQuestion> getAllQuestions() {
        return repo.findAll();
    }

    @Override
    public Optional<MCQQuestion> getQuestionById(Long id) {
        return repo.findById(id);
    }

    @Override
    public MCQQuestion updateQuestionById(MCQQuestion question) {
        return repo.save(question);
    }

    @Override
    public void deleteQuestionById(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception ex) {
            throw new MCQQuestionDeleteException("Failed to delete question with id: " + id);
        }
    }
    
}
