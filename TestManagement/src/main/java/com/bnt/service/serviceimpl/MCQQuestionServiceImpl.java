package com.bnt.service.serviceimpl;

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

import com.bnt.entities.MCQQuestion;
import com.bnt.entities.Subcategory;
import com.bnt.exception.InvalidFileException;
import com.bnt.exception.MCQQuestionDeleteException;
import com.bnt.exception.MCQQuestionDuplicateException;
import com.bnt.repository.MCQQuestionRepository;
import com.bnt.service.CategoryService;
import com.bnt.service.MCQQuestionService;
import com.bnt.service.SubcategoryService;

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
            throw new MCQQuestionDuplicateException("MCQ question with "+question.getQuestion()+" is already present.");
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
        } catch (IOException e) {
            log.error("Failed to initialize workbook in uploadBulkQuestions method in MCQQuestionServiceImpl.", e);
        }
        Sheet sheet = workbook.getSheet("MCQ");
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Here we skiped header row
            MCQQuestion question = new MCQQuestion();

            log.debug("Processing row: {}", row.getRowNum());


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
        try{
            return mcqQuestionRepository.findAll();
        }catch(Exception ex){
            log.error("Failed to fetch all questions", ex);
            return null;
        }
        
    }

    @Override
    public Optional<MCQQuestion> getQuestionById(Long id) {
        try{
            return mcqQuestionRepository.findById(id);
        }catch(Exception ex){
            log.error("Failed to fetch question by id: {}",id);
            return null;
        }
        
    }

    @Override
    public MCQQuestion updateQuestionById(MCQQuestion question) {
        try{
            return mcqQuestionRepository.save(question);
        }catch(Exception ex){
            log.error("Failed to update question with id: {}",question.getId());
            return null;
        }
       
    }

    @Override
    public void deleteQuestionById(Long id) {
        try {
            mcqQuestionRepository.deleteById(id);
        } catch (Exception ex) {
             throw new MCQQuestionDeleteException("Failed to delete question with id: " + id);
        }
    }
    
}
