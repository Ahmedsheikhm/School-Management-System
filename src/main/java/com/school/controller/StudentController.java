package com.school.controller;

import com.school.entity.Student;
import com.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentController {
    
    private final StudentService studentService;
    
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    
    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Student.class)))
    })
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Student> getStudentById(
            @Parameter(description = "ID of the student to retrieve") @PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Get student by email", description = "Retrieve a student by their email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved student"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Student> getStudentByEmail(
            @Parameter(description = "Email of the student to retrieve") @PathVariable String email) {
        Optional<Student> student = studentService.getStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/grade/{gradeLevel}")
    @Operation(summary = "Get students by grade level", description = "Retrieve students by their grade level")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students")
    })
    public ResponseEntity<List<Student>> getStudentsByGradeLevel(
            @Parameter(description = "Grade level to filter by") @PathVariable Integer gradeLevel) {
        List<Student> students = studentService.getStudentsByGradeLevel(gradeLevel);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/gpa/{gpa}")
    @Operation(summary = "Get students with GPA above threshold", description = "Retrieve students with GPA above specified value")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved students")
    })
    public ResponseEntity<List<Student>> getStudentsWithGpaAbove(
            @Parameter(description = "Minimum GPA threshold") @PathVariable Double gpa) {
        List<Student> students = studentService.getStudentsWithGpaAbove(gpa);
        return ResponseEntity.ok(students);
    }
    
    @PostMapping
    @Operation(summary = "Create a new student", description = "Create a new student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Student> createStudent(
            @Parameter(description = "Student object to create") @Valid @RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a student", description = "Update an existing student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Student> updateStudent(
            @Parameter(description = "ID of the student to update") @PathVariable Long id,
            @Parameter(description = "Updated student object") @Valid @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Delete a student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found"),
        @ApiResponse(responseCode = "400", description = "Cannot delete student that is enrolled in classes")
    })
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID of the student to delete") @PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 