package com.test.service.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.entities.MCQQuestion;
import com.test.entities.Subcategory;
import com.test.exception.DataDeleteException;
import com.test.exception.DataDuplicateException;
import com.test.exception.DataNotFoundException;
import com.test.exception.InvalidFileException;
import com.test.exception.UnexpectedException;
import com.test.repository.MCQQuestionRepository;
import com.test.service.CategoryService;
import com.test.service.MCQQuestionService;
import com.test.service.SubcategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MCQQuestionServiceImpl implements MCQQuestionService {

    private static final  int  NUMERIC = 0;
    private static final int STRING = 1;
    @Autowired
    private MCQQuestionRepository mcqQuestionRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    @Override
    public MCQQuestion createQuestion(MCQQuestion question) {
        log.info("Processing on mcq question object in createQuestion method in MCQQuestionServiceImpl for checking all field contain data.");
        if(question.getSubcategory()==null||question.getQuestion()==null||question.getOptionOne()==null||question.getOptionTwo()==null||question.getOptionThree()==null||question.getOptionFour()==null||question.getCorrectOption()==null||question.getPositiveMark()==null||question.getNegativeMark()==null){
            StringBuilder message=new StringBuilder("Provided mcq question object not contain proper data.");
            throw new IllegalArgumentException(message.toString());
        }
        Long id=mcqQuestionRepository.findQuestionIdByQuestionName(question.getQuestion());
        if(id!=null){
            throw new DataDuplicateException("MCQ question with "+question.getQuestion()+" is already present.");
        }
        log.info("MCQ Question creation started.");
        return mcqQuestionRepository.save(question);
    }

    @Override
    public Map<String , List<Object>> uploadBulkQuestions(MultipartFile file) {
        log.info("Processing on provided file in uploadBulkQuestions method in MCQQuestionServiceImpl.");
        if(!file.getOriginalFilename().endsWith(".xlsx")){
            throw new InvalidFileException("Invalid file format. Only .xlsx files are allowed.");
        }

        Map<String, List<Object>> map=new LinkedHashMap<>();

        List <Object> faildCategoryList=new ArrayList<>();
        faildCategoryList.add("Following category not present in database. So questions which contain following category not added in database.");

        List <Object> faildSubcategoryList=new ArrayList<>();
        faildSubcategoryList.add("Following subcategory not present in database. So questions which contain following subcategory not added in database.");

        List<Object> alreadyPresentQuestionList=new ArrayList<>();
        alreadyPresentQuestionList.add("Following questions already present in database.");

        Workbook workbook=null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            log.info("Workbook initialized successfully");
        } catch (IOException ex) {
            log.error("Failed to initialize workbook in uploadBulkQuestions method in MCQQuestionServiceImpl.", ex);
            throw new UnexpectedException("Unexpected error occurred"+ex.getMessage());
        }
        Sheet sheet = workbook.getSheet("MCQ");
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Here we skiped header row
            MCQQuestion question = new MCQQuestion();

            log.info("Processing row: {}", row.getRowNum());

            Long categoryId = categoryService.getCategoryIdByCategoryName(row.getCell(1).getStringCellValue());
            if(categoryId<1||categoryId==null){
                faildCategoryList.add(row.getCell(1).getStringCellValue());
                log.info("Skiped category {} because not found in database",row.getCell(1).getStringCellValue());
                continue;
            }

            Long subcategoryId=subcategoryService.getSubcategoryIdBySubcategoryName(row.getCell(2).getStringCellValue());
            if(subcategoryId==null||subcategoryId<1){
                faildSubcategoryList.add(row.getCell(2).getStringCellValue());
                log.info("Skiped subcategory {} because not found in database",row.getCell(2).getStringCellValue());
                continue;
            }
            Optional<Subcategory> subcategory=subcategoryService.getSubcategoryById(subcategoryId);

            question.setSubcategory(subcategory.get());

            String questionString=row.getCell(3).getStringCellValue();

            Long id=mcqQuestionRepository.findQuestionIdByQuestionName(questionString);
            if(id!=null){
                alreadyPresentQuestionList.add("Q.Id "+id+" "+questionString);
                log.info("Skiped question {} because already present in database",questionString);
                continue;
            }

            question.setQuestion(questionString);
            question.setOptionOne(row.getCell(4).getStringCellValue());
            question.setOptionTwo(row.getCell(5).getStringCellValue());
            question.setOptionThree(row.getCell(6).getStringCellValue());
            question.setOptionFour(row.getCell(7).getStringCellValue());
            question.setCorrectOption(row.getCell(8).getStringCellValue());
            question.setPositiveMark((int) row.getCell(9).getNumericCellValue());
            switch (row.getCell(10).getCellType()) {
                case NUMERIC:
                    question.setNegativeMark((int) row.getCell(10).getNumericCellValue());
                    break;
                case STRING:
                    try {
                        question.setNegativeMark(Integer.parseInt(row.getCell(10).getStringCellValue().trim()));
                    } catch (NumberFormatException e) {
                        log.error("Failed to parse negative mark as integer for row {}", row.getRowNum(), e);
                    }
                    break;
                default:log.error("Failed to retrive negative marks from cell in uploadBulkQuestions method in MCQQuestionServiceImpl.");
            }
            mcqQuestionRepository.save(question);
        }

        List<Object> sucssessList=new ArrayList<>();
        sucssessList.add("File uploaded and questions created successfully.");
        map.put("Success Message", sucssessList);

        if(faildCategoryList.size()>1){
            map.put("Category", faildSubcategoryList);
        }
        if(faildSubcategoryList.size()>1){
            map.put("Subcategory", faildSubcategoryList);
        }
        if(alreadyPresentQuestionList.size()>1){
            map.put("Questions", alreadyPresentQuestionList);
        }
        return map;
    }

    @Override
    public List<MCQQuestion> getAllQuestions() {
        List<MCQQuestion> mcqQuestionList = mcqQuestionRepository.findAll();
        if(mcqQuestionList==null){
            throw new DataNotFoundException("Categories not present in database");
        }
        return mcqQuestionList;        
    }

    @Override
    public Optional<MCQQuestion> getQuestionById(Long id) {
        Optional<MCQQuestion> mcqQuestion = mcqQuestionRepository.findById(id);
        if(!mcqQuestion.isPresent()){
            StringBuilder message=new StringBuilder("MCQQuestion with Id: ").append(id).append(" not present in database.");
            throw new DataNotFoundException(message.toString());
        }
        return mcqQuestion;
    }

    @Override
    public MCQQuestion updateQuestionById(Long id, MCQQuestion question) {
        log.info("Updating question in MCQQuestion service implement with id: {}", id);
        if (!this.getQuestionById(id).isPresent()) {
            StringBuilder message=new StringBuilder("MCQQuestion with Id: ").append(id).append(" not present in database.");
            throw new DataNotFoundException(message.toString());
        }
        question.setId(id);
        return mcqQuestionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(Long id) {
        try {
            if (!this.getQuestionById(id).isPresent()) {
                StringBuilder message=new StringBuilder("MCQQuestion with Id: ").append(id).append(" not present in database.");
                throw new DataNotFoundException(message.toString());
            }
            mcqQuestionRepository.deleteById(id);
        } catch (Exception ex) {
            String errorMessage = "Failed to delete question with id: " + id;
            log.error(errorMessage, ex);
            throw new DataDeleteException(errorMessage+" "+ex.getMessage());
        }
    }  
}
