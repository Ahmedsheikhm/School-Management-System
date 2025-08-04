package com.school.controller;

import com.school.entity.Subject;
import com.school.service.SubjectService;
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
@RequestMapping("/api/subjects")
@Tag(name = "Subject Management", description = "APIs for managing subjects")
public class SubjectController {
    
    private final SubjectService subjectService;
    
    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    @GetMapping
    @Operation(summary = "Get all subjects", description = "Retrieve a list of all subjects")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved subjects",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Subject.class)))
    })
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get subject by ID", description = "Retrieve a subject by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved subject"),
        @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    public ResponseEntity<Subject> getSubjectById(
            @Parameter(description = "ID of the subject to retrieve") @PathVariable Long id) {
        Optional<Subject> subject = subjectService.getSubjectById(id);
        return subject.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    @Operation(summary = "Get subject by name", description = "Retrieve a subject by its name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved subject"),
        @ApiResponse(responseCode = "404", description = "Subject not found")
    })
    public ResponseEntity<Subject> getSubjectByName(
            @Parameter(description = "Name of the subject to retrieve") @PathVariable String name) {
        Optional<Subject> subject = subjectService.getSubjectByName(name);
        return subject.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Create a new subject", description = "Create a new subject")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Subject created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Subject> createSubject(
            @Parameter(description = "Subject object to create") @Valid @RequestBody Subject subject) {
        try {
            Subject createdSubject = subjectService.createSubject(subject);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update a subject", description = "Update an existing subject")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subject updated successfully"),
        @ApiResponse(responseCode = "404", description = "Subject not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Subject> updateSubject(
            @Parameter(description = "ID of the subject to update") @PathVariable Long id,
            @Parameter(description = "Updated subject object") @Valid @RequestBody Subject subjectDetails) {
        try {
            Subject updatedSubject = subjectService.updateSubject(id, subjectDetails);
            return ResponseEntity.ok(updatedSubject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subject", description = "Delete a subject by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Subject deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Subject not found"),
        @ApiResponse(responseCode = "400", description = "Cannot delete subject that is used in classes")
    })
    public ResponseEntity<Void> deleteSubject(
            @Parameter(description = "ID of the subject to delete") @PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 