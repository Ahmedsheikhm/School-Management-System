# School Management System

A comprehensive Spring Boot application for managing school data including students, teachers, subjects, and classes with RESTful API endpoints.

## Features

- **Student Management**: CRUD operations for students with GPA tracking
- **Teacher Management**: CRUD operations for teachers with specialization tracking
- **Subject Management**: CRUD operations for subjects with credit tracking
- **Class Management**: CRUD operations for classes with student enrollment
- **RESTful API**: Complete REST API with OpenAPI documentation
- **Database**: H2 in-memory database with sample data
- **Docker Support**: Containerized application with docker-compose

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **OpenAPI 3.0**
- **Docker**


## Database Schema

The application uses the following entities and relationships:

- **Subject**: Core subjects taught in the school
- **Teacher**: Teachers with specializations and salary information
- **Student**: Students with personal information and GPA
- **Class**: Classes that connect subjects, teachers, and students

### Relationships:
- One Subject can have many Classes
- One Teacher can teach many Classes
- Many Students can be enrolled in many Classes (Many-to-Many)
- Each Class belongs to one Subject and one Teacher

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (optional)

### Running Locally

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd school-app
   ```

2. **Build the application**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api-docs

### Running with Docker

1. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

2. **Access the application**
   - Application: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

## API Endpoints

### Subjects
- `GET /api/subjects` - Get all subjects
- `GET /api/subjects/{id}` - Get subject by ID
- `GET /api/subjects/name/{name}` - Get subject by name
- `POST /api/subjects` - Create new subject
- `PUT /api/subjects/{id}` - Update subject
- `DELETE /api/subjects/{id}` - Delete subject

### Teachers
- `GET /api/teachers` - Get all teachers
- `GET /api/teachers/{id}` - Get teacher by ID
- `GET /api/teachers/email/{email}` - Get teacher by email
- `POST /api/teachers` - Create new teacher
- `PUT /api/teachers/{id}` - Update teacher
- `DELETE /api/teachers/{id}` - Delete teacher

### Students
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `GET /api/students/email/{email}` - Get student by email
- `GET /api/students/grade/{gradeLevel}` - Get students by grade level
- `GET /api/students/gpa/{gpa}` - Get students with GPA above threshold
- `POST /api/students` - Create new student
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

### Classes
- `GET /api/classes` - Get all classes
- `GET /api/classes/{id}` - Get class by ID
- `GET /api/classes/subject/{subjectId}` - Get classes by subject
- `GET /api/classes/teacher/{teacherId}` - Get classes by teacher
- `GET /api/classes/student/{studentId}` - Get classes by student
- `GET /api/classes/semester?semester={semester}&academicYear={year}` - Get classes by semester
- `GET /api/classes/available` - Get available classes
- `POST /api/classes` - Create new class
- `PUT /api/classes/{id}` - Update class
- `DELETE /api/classes/{id}` - Delete class
- `POST /api/classes/{classId}/enroll/{studentId}` - Enroll student in class
- `DELETE /api/classes/{classId}/enroll/{studentId}` - Remove student from class

## Sample Data

The application comes with pre-loaded sample data including:
- 5 Subjects (Mathematics, Physics, Chemistry, English Literature, World History)
- 5 Teachers with different specializations
- 5 Students with various grade levels and GPAs
- 5 Classes with different schedules and enrollments

## Database Configuration

- **Database**: H2 in-memory database
- **Console**: Available at http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:school_db
- **Username**: sa
- **Password**: password

## API Documentation

The API is documented using OpenAPI 3.0 and can be accessed at:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## Testing

### Manual Testing

You can test the API endpoints using:

1. **Swagger UI**: Interactive API documentation
2. **cURL**: Command-line HTTP client
3. **Postman**: API testing tool

### Example cURL Commands

```bash
# Get all students
curl -X GET "http://localhost:8080/api/students"

# Create a new student
curl -X POST "http://localhost:8080/api/students" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@student.com",
    "phone": "+1234567890",
    "dateOfBirth": "2005-01-01",
    "enrollmentDate": "2020-09-01",
    "address": "123 Test St",
    "gradeLevel": 10,
    "gpa": 3.5
  }'

# Enroll a student in a class
curl -X POST "http://localhost:8080/api/classes/1/enroll/1"
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/school/
│   │       ├── SchoolApplication.java
│   │       ├── config/
│   │       │   └── DataLoader.java
│   │       ├── controller/
│   │       │   ├── SubjectController.java
│   │       │   ├── TeacherController.java
│   │       │   ├── StudentController.java
│   │       │   └── ClassController.java
│   │       ├── entity/
│   │       │   ├── Subject.java
│   │       │   ├── Teacher.java
│   │       │   ├── Student.java
│   │       │   └── Class.java
│   │       ├── repository/
│   │       │   ├── SubjectRepository.java
│   │       │   ├── TeacherRepository.java
│   │       │   ├── StudentRepository.java
│   │       │   └── ClassRepository.java
│   │       └── service/
│   │           ├── SubjectService.java
│   │           ├── TeacherService.java
│   │           ├── StudentService.java
│   │           └── ClassService.java
│   └── resources/
│       └── application.yml
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 