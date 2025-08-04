package com.school.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Home", description = "Home page and navigation")
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Home page", description = "Welcome page with navigation links")
    public String home() {
        return """
            <html>
            <head>
                <title>School Management API</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; }
                    .container { max-width: 800px; margin: 0 auto; }
                    .link { color: #007bff; text-decoration: none; }
                    .link:hover { text-decoration: underline; }
                    .section { margin: 20px 0; padding: 15px; border: 1px solid #ddd; border-radius: 5px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🏫 School Management API</h1>
                    <p>Welcome to the School Management System API. This application provides RESTful endpoints for managing schools, students, teachers, classes, and subjects.</p>
                    
                    <div class="section">
                        <h2>📚 API Documentation</h2>
                        <ul>
                            <li><a href="/swagger-ui.html" class="link">Swagger UI - Interactive API Documentation</a></li>
                            <li><a href="/api-docs" class="link">OpenAPI JSON Specification</a></li>
                        </ul>
                    </div>
                    
                    <div class="section">
                        <h2>🗄️ Database Console</h2>
                        <ul>
                            <li><a href="/h2-console" class="link">H2 Database Console</a></li>
                            <p><strong>Database URL:</strong> jdbc:h2:mem:school_db</p>
                            <p><strong>Username:</strong> sa</p>
                            <p><strong>Password:</strong> (leave empty)</p>
                        </ul>
                    </div>
                    
                    <div class="section">
                        <h2>🚀 Available Endpoints</h2>
                        <ul>
                            <li><strong>Subjects:</strong> <a href="/api/subjects" class="link">/api/subjects</a></li>
                            <li><strong>Teachers:</strong> <a href="/api/teachers" class="link">/api/teachers</a></li>
                            <li><strong>Students:</strong> <a href="/api/students" class="link">/api/students</a></li>
                            <li><strong>Classes:</strong> <a href="/api/classes" class="link">/api/classes</a></li>
                        </ul>
                    </div>
                    
                    <div class="section">
                        <h2>📝 Sample Data</h2>
                        <p>The application comes pre-loaded with sample data including subjects, teachers, students, and classes with their relationships.</p>
                    </div>
                </div>
            </body>
            </html>
            """;
    }
} 