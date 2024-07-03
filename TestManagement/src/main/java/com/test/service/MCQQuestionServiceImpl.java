package com.test.service;

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
import com.test.exception.MCQQuestionDeleteException;
import com.test.repository.MCQQuestionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MCQQuestionServiceImpl implements MCQQuestionService {

    private static final Logger logger = LoggerFactory.getLogger(MCQQuestionServiceImpl.class);
   

    private static final  int  NUMERIC = 0;
    private static final int STRING = 1;
    @Autowired
    private MCQQuestionRepository repo;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubcategoryService subcategoryService;

    @Override
    public MCQQuestion createQuestion(MCQQuestion question) {
        return repo.save(question);
    }

    @Override
    public Map<String , List<Object>> uploadBulkQuestions(MultipartFile file) {

        Map<String, List<Object>> map=new LinkedHashMap<>();

        List <Object> faildCategoryList=new ArrayList<Object>();
        faildCategoryList.add("Following category not present in database. So questions which contain following category not added in database.");

        List <Object> faildSubcategoryList=new ArrayList<Object>();
        faildSubcategoryList.add("Following subcategory not present in database. So questions which contain following subcategory not added in database.");
        Workbook workbook=null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            logger.info("Workbook initialized successfully");
        } catch (IOException e) {
            logger.error("Failed to initialize workbook", e);
        }
        Sheet sheet = workbook.getSheet("MCQ");
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Here we skiped header row
            MCQQuestion question = new MCQQuestion();

            logger.debug("Processing row: {}", row.getRowNum());


            Long categoryId = categoryService.getCategoryIdByCategoryName(row.getCell(1).getStringCellValue());
            if(categoryId<1||categoryId==null){
                faildCategoryList.add(row.getCell(1).getStringCellValue());
                logger.info("Skiped category {} because not found in database",row.getCell(1).getStringCellValue());
                continue;
            }

            Long subcategoryId=subcategoryService.getSubcategoryIdBySubcategoryName(row.getCell(2).getStringCellValue());
            if(subcategoryId==null||subcategoryId<1){
                faildSubcategoryList.add(row.getCell(2).getStringCellValue());
                logger.info("Skiped subcategory {} because not found in database",row.getCell(2).getStringCellValue());
                continue;
            }
            Optional<Subcategory> subcategory=subcategoryService.getSubcategoryById(subcategoryId);

            question.setSubcategory(subcategory.get());
            question.setQuestion(row.getCell(3).getStringCellValue());
            question.setOptionOne(row.getCell(4).getStringCellValue());
            question.setOptionTwo(row.getCell(5).getStringCellValue());
            question.setOptionThree(row.getCell(6).getStringCellValue());
            question.setOptionFour(row.getCell(7).getStringCellValue());
            question.setCorrectOption(row.getCell(8).getStringCellValue());
            question.setPositiveMark((int) row.getCell(9).getNumericCellValue());
             //question.setNegativeMark(Integer.parseInt(row.getCell(10).getStringCellValue()));
            //question.setNegativeMark((int)row.getCell(10).getNumericCellValue());
            switch (row.getCell(10).getCellType()) {
                case NUMERIC:
                    question.setNegativeMark((int) row.getCell(10).getNumericCellValue());
                    break;
                case STRING:
                    try {
                        question.setNegativeMark(Integer.parseInt(row.getCell(10).getStringCellValue().trim()));
                    } catch (NumberFormatException e) {
                        logger.error("Failed to parse negative mark as integer for row {}", row.getRowNum(), e);
                    }
                    break;
            }
            repo.save(question);

        }
        // workbook.close();

        if(faildCategoryList.size()>1){
            map.put("Category", faildSubcategoryList);
        }
        if(faildSubcategoryList.size()>1){
            map.put("Subcategory", faildSubcategoryList);
        }

        return map;
    }

    @Override
    public List<MCQQuestion> getAllQuestions() {
        try{
            return repo.findAll();
        }catch(Exception ex){
            logger.error("Failed to fetch all questions", ex);
            return null;
        }
        
    }

    @Override
    public Optional<MCQQuestion> getQuestionById(Long id) {
        try{
            return repo.findById(id);
        }catch(Exception ex){
            logger.error("Failed to fetch question by id: {}",id);
            return null;
        }
        
    }

    @Override
    public MCQQuestion updateQuestionById(MCQQuestion question) {
        try{
            return repo.save(question);
        }catch(Exception ex){
            logger.error("Failed to update question with id: {}",question.getId());
            return null;
        }
       
    }

    @Override
    public void deleteQuestionById(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception ex) {
            logger.error("Failed to delete question with id: {}",id);
            throw new MCQQuestionDeleteException("Failed to delete question with id: " + id);
        }
    }
    
}
