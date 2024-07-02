# TestManagement
This project is a Test Management System implemented using Spring Boot and Java. It provides RESTful APIs to manage multiple-choice questions (MCQs) for tests.


**Features**
Create, read, update, and delete MCQ questions, Category and Subcategory.
Retrieve a list of all MCQ questions or a specific question by its ID.
Retrieve a list of all category or a specific category by its ID.
Retrieve a list of all subcategory or a specific category by its ID.


**Technologies Used**
Java
Spring Boot
Spring Data JPA
H2 Database (for demo purposes; can be configured to any database)


**Getting Started**
To run this project locally, follow these steps:

Clone the repository:
git clone https://github.com/vaibhavshejol/TestManagement.git

Navigate to the project directory
cd TestManagement


**Run the application**
You can run the application using directly from your IDE.
Open the project in your IDE and run TestManagementApplication.java as a Java application.


**Access the application**
Once the application is running, you can access it at:
http://localhost:8080


**API Endpoints:**
**For MCQ Questions**
POST /questions
Create a new MCQ question. Use JSON format with fields: question, option1, option2, option3, option4, correctAnswer.

GET /questions
Retrieve all MCQ questions.

GET /questions/{id}
Retrieve a specific MCQ question by its ID.

PUT /questions/{id}
Update an existing MCQ question by its ID. Use JSON format with fields: subcategoryId, question, option1, option2, option3, option4, correctAnswer, positiveMark, negativeMark.

DELETE /questions/{id}
Delete an MCQ question by its ID.


**API Endpoints:**
**For category**
POST /category
Create a new category. Use JSON format with fields: categoryId, categoryName, categoryDescription.

GET /category
Retrieve all category.

GET /category/{id}
Retrieve a specific category by its ID.

PUT /category/{id}
Update an existing category by its ID. Use JSON format with fields: categoryId, categoryName, categoryDescription.

DELETE /category/{id}
Delete an category by its ID.


**API Endpoints:**
**For Subcategory**
POST /subcategory
Create a new subcategory. Use JSON format with fields: subcategoryId, categoryId, categoryName, categoryDescription.

GET /subcategory
Retrieve all subcategory.

GET /subcategory/{id}
Retrieve a specific subcategory by its ID.

PUT /subcategory/{id}
Update an existing subcategory by its ID. Use JSON format with fields: subcategoryId, categoryId, categoryName, categoryDescription.

DELETE /subcategory/{id}
Delete an subcategory by its ID.


**Sample JSON for Category Object:**
{
    "categoryId": 1,
    "categoryName": "Java",
    "categoryDescription": "Core Java category"
}


**Sample JSON for Subcategory Object:**
{
    "subcategoryId": 1,
    "subcategoryName": "Collection",
    "subcategoryDescription": "Collection form Java",
    "category": {
        "categoryId": 1,
        "categoryName": "Java",
        "categoryDescription": "Core Java category"
    }
}


**Sample JSON for Question Object:**
{
	"subcategory":{
		"subcategoryId":4,
        "subcategoryName":"Annotation",
        "subcategoryDescription": "Annotations in Spring",
        "category": {
            "categoryId": 3,
            "categoryName": "Spring Boot",
            "categoryDescription": "Spring Boot Framework category"
        }
	},
	"question": "In Spring Boot @RestController annotation is equivalent to",
	"optionOne": "@Controller and @PostMapping",
	"optionTwo": "@Controller and @Component",
	"optionThree": "@Controller and @ResponseBody",
	"optionFour": "@Controller and @ResponseStatus",
	"correctOption": "@Controller and @ResponseBody",
	"positiveMark": "3",
	"negativeMark": "-1"
}


**Notes:**
This project uses an in-memory H2 database by default. You can configure the database settings in application.properties file if you want to use a different database.

Make sure you have Maven and Java installed to build and run the project.
