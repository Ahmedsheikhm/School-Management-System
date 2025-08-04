package com.school.controller;

import com.school.entity.Class;
import com.school.service.ClassService;
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
@RequestMapping("/api/classes")
@Tag(name = "Class Management", description = "APIs for managing classes and enrollments")
public class ClassController {
    
    private final ClassService classService;
    
    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }
    
    @GetMapping
    @Operation(summary = "Get all classes", description = "Retrieve a list of all classes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved classes",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Class.class)))
    })
    public ResponseEntity<List<Class>> getAllClasses() {
        List<Class> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get class by ID", description = "Retrieve a class by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved class"),
        @ApiResponse(responseCode = "404", description = "Class not found")
    })
    public ResponseEntity<Class> getClassById(
            @Parameter(description = "ID of the class to retrieve") @PathVariable Long id) {
        Optional<Class> classEntity = classService.getClassById(id);
        return classEntity.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/subject/{subjectId}")
    @Operation(summary = "Get classes by subject", description = "Retrieve classes by subject ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved classes")
    })
    public ResponseEntity<List<Class>> getClassesBySubject(
            @Parameter(description = "Subject ID to filter by") @PathVariable Long subjectId) {
        List<Class> classes = classService.getClassesBySubject(subjectId);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get classes by teacher", description = "Retrieve classes by teacher ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved classes")
    })
    public ResponseEntity<List<Class>> getClassesByTeacher(
            @Parameter(description = "Teacher ID to filter by") @PathVariable Long teacherId) {
        List<Class> classes = classService.getClassesByTeacher(teacherId);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get classes by student", description = "Retrieve classes by student ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved classes")
    })
    public ResponseEntity<List<Class>> getClassesByStudent(
            @Parameter(description = "Student ID to filter by") @PathVariable Long studentId) {
        List<Class> classes = classService.getClassesByStudent(studentId);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/semester")
    @Operation(summary = "Get classes by semester and year", description = "Retrieve classes by semester and academic year")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved classes")
    })
    public ResponseEntity<List<Class>> getClassesBySemesterAndYear(
            @Parameter(description = "Semester") @RequestParam String semester,
            @Parameter(description = "Academic year") @RequestParam String academicYear) {
        List<Class> classes = classService.getClassesBySemesterAndYear(semester, academicYear);
        return ResponseEntity.ok(classes);
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available classes", description = "Retrieve classes that have available capacity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved available classes")
    })
    public ResponseEntity<List<Class>> getAvailableClasses() {
        List<Class> classes = classService.getAvailableClasses();
        return ResponseEntity.ok(classes);
    }
    
    @PostMapping
    @Operation(summary = "Create a new class", description = "Create a new class")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Class created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Class> createClass(
            @Parameter(description = "Class object to create") @Valid @RequestBody Class classEntity) {
        try {
            Class createdClass = classService.createClass(classEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a class", description = "Update an existing class")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Class updated successfully"),
        @ApiResponse(responseCode = "404", description = "Class not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Class> updateClass(
            @Parameter(description = "ID of the class to update") @PathVariable Long id,
            @Parameter(description = "Updated class object") @Valid @RequestBody Class classDetails) {
        try {
            Class updatedClass = classService.updateClass(id, classDetails);
            return ResponseEntity.ok(updatedClass);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a class", description = "Delete a class by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Class deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Class not found")
    })
    public ResponseEntity<Void> deleteClass(
            @Parameter(description = "ID of the class to delete") @PathVariable Long id) {
        try {
            classService.deleteClass(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{classId}/enroll/{studentId}")
    @Operation(summary = "Enroll student in class", description = "Enroll a student in a specific class")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student enrolled successfully"),
        @ApiResponse(responseCode = "400", description = "Class is full or student already enrolled"),
        @ApiResponse(responseCode = "404", description = "Class or student not found")
    })
    public ResponseEntity<Void> enrollStudent(
            @Parameter(description = "Class ID") @PathVariable Long classId,
            @Parameter(description = "Student ID") @PathVariable Long studentId) {
        try {
            classService.enrollStudent(classId, studentId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{classId}/enroll/{studentId}")
    @Operation(summary = "Remove student from class", description = "Remove a student from a specific class")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student removed successfully"),
        @ApiResponse(responseCode = "400", description = "Student not enrolled in class"),
        @ApiResponse(responseCode = "404", description = "Class or student not found")
    })
    public ResponseEntity<Void> removeStudent(
            @Parameter(description = "Class ID") @PathVariable Long classId,
            @Parameter(description = "Student ID") @PathVariable Long studentId) {
        try {
            classService.removeStudent(classId, studentId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 