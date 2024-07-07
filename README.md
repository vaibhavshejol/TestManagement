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
- PostgreSQL Database (for demo, can be replaced with other databases like MySQL)
- Apache POI (for Excel file handling)
- Lombok (for reducing boilerplate code)
- Gradle (for dependency management)

## Getting Started

### Prerequisites

- Java 8 or higher
- Gradle or Maven
- IDE (vscode, IntelliJ IDEA, Eclipse, etc.)

### Installation

1. Clone the repository
   ```sh
   git clone https://github.com/vaibhavshejol/TestManagement.git
Open the project in your IDE
Build the project using Maven
Usage
API Endpoints
# Access the application
The application provides the following RESTful APIs:
Once the application is running, you can access it at:
http://localhost:8080

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
- Create MCQ Question<br>
Endpoint: POST /questions<br>
Request Body: JSON containing all fields required for an MCQ question
- Upload Bulk MCQ Questions<br>
Endpoint: POST /questions/uploadBulkQuestions<br>
Request Body: Multipart file (Excel format)
- Get All MCQ Questions<br>
Endpoint: GET /questions<br>
- Get MCQ Question by Id<br>
Endpoint: GET /questions/{id}<br>
- Update MCQ Question by Id<br>
Endpoint: PUT /questions/{id}<br>
Request Body: Updated JSON containing fields to update
- Delete MCQ Question by Id<br>
Endpoint: DELETE /questions/{id}<br>

### Sample JSON for Category Object:
{
    "categoryId": 1,
    "categoryName": "Java",
    "categoryDescription": "Core Java category"
}

### Sample JSON for Subcategory Object:
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

### Sample JSON for Question Object:
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
