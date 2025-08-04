package com.school.controller;

import com.school.entity.Teacher;
import com.school.service.TeacherService;
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
@RequestMapping("/api/teachers")
@Tag(name = "Teacher Management", description = "APIs for managing teachers")
public class TeacherController {
    
    private final TeacherService teacherService;
    
    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    
    @GetMapping
    @Operation(summary = "Get all teachers", description = "Retrieve a list of all teachers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved teachers",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Teacher.class)))
    })
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get teacher by ID", description = "Retrieve a teacher by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved teacher"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<Teacher> getTeacherById(
            @Parameter(description = "ID of the teacher to retrieve") @PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Get teacher by email", description = "Retrieve a teacher by their email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved teacher"),
        @ApiResponse(responseCode = "404", description = "Teacher not found")
    })
    public ResponseEntity<Teacher> getTeacherByEmail(
            @Parameter(description = "Email of the teacher to retrieve") @PathVariable String email) {
        Optional<Teacher> teacher = teacherService.getTeacherByEmail(email);
        return teacher.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create a new teacher", description = "Create a new teacher")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Teacher created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Teacher> createTeacher(
            @Parameter(description = "Teacher object to create") @Valid @RequestBody Teacher teacher) {
        try {
            Teacher createdTeacher = teacherService.createTeacher(teacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a teacher", description = "Update an existing teacher")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teacher updated successfully"),
        @ApiResponse(responseCode = "404", description = "Teacher not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Teacher> updateTeacher(
            @Parameter(description = "ID of the teacher to update") @PathVariable Long id,
            @Parameter(description = "Updated teacher object") @Valid @RequestBody Teacher teacherDetails) {
        try {
            Teacher updatedTeacher = teacherService.updateTeacher(id, teacherDetails);
            return ResponseEntity.ok(updatedTeacher);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a teacher", description = "Delete a teacher by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Teacher deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Teacher not found"),
        @ApiResponse(responseCode = "400", description = "Cannot delete teacher that is assigned to classes")
    })
    public ResponseEntity<Void> deleteTeacher(
            @Parameter(description = "ID of the teacher to delete") @PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 