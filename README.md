# Test Management Application

This project is a Test Management Application designed to manage multiple-choice questions (MCQs) categorized under various categories and subcategories.

# Features

- **Category Management**:
  - Create, read, update, and delete categories.
- **Subcategory Management**:
  - Create, read, update, and delete subcategories associated with categories.
- **MCQ Question Management**:
  - Create, read, update, and delete multiple-choice questions with options, marks, and correct answers.
  - Bulk upload of MCQ questions via Excel file.

# Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (for demo, can be replaced with other databases like MySQL, PostgreSQL)
- Apache POI (for Excel file handling)
- Lombok (for reducing boilerplate code)
- Maven (for dependency management)

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/yourusername/test-management.git
Open the project in your IDE
Build the project using Maven
Usage
API Endpoints
The application provides the following RESTful APIs:

#### Category Management
- Create Category<br>
Endpoint: POST /category<br>
Request Body: JSON containing categoryName and optionally categoryDescription
- Get All Categories<br>
Endpoint: GET /category<br>
- Get Category by Id<br>
Endpoint: GET /category/{id}<br>
- Update Category by Id<br>
Endpoint: PUT /category/{id}<br>
Request Body: Updated JSON containing categoryName and optionally categoryDescription
- Delete Category by Id<br>
Endpoint: DELETE /category/{id}<br>
#### Subcategory Management
- Create Subcategory<br>
Endpoint: POST /subcategory<br>
Request Body: JSON containing subcategoryName and optionally subcategoryDescription
- Get All Subcategories<br>
Endpoint: GET /subcategory<br>
- Get Subcategory by Id<br>
Endpoint: GET /subcategory/{id}<br>
- Update Subcategory by Id<br>
Endpoint: PUT /subcategory/{id}<br>
Request Body: Updated JSON containing subcategoryName and optionally subcategoryDescription
- Delete Subcategory by Id<br>
Endpoint: DELETE /subcategory/{id}<br>
#### MCQ Question Management
- Create MCQ Question
Endpoint: POST /questions
Request Body: JSON containing all fields required for an MCQ question
- Upload Bulk MCQ Questions
Endpoint: POST /questions/uploadBulkQuestions
Request Body: Multipart file (Excel format)
- Get All MCQ Questions
Endpoint: GET /questions
- Get MCQ Question by Id
Endpoint: GET /questions/{id}
- Update MCQ Question by Id
Endpoint: PUT /questions/{id}
Request Body: Updated JSON containing fields to update
- Delete MCQ Question by Id
Endpoint: DELETE /questions/{id}
Example
To create a category:
http
Copy code
POST /category
Content-Type: application/json

{
  "categoryName": "Sample Category",
  "categoryDescription": "Description of the sample category"
}

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
