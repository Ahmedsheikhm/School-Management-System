package com.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Subject name is required")
    @Size(min = 2, max = 100, message = "Subject name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description")
    private String description;
    
    @Column(name = "credits")
    private Integer credits;
    
    // Relationships
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Class> classes = new HashSet<>();
    
    // Constructors
    public Subject() {}
    
    public Subject(String name, String description, Integer credits) {
        this.name = name;
        this.description = description;
        this.credits = credits;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getCredits() {
        return credits;
    }
    
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    
    public Set<Class> getClasses() {
        return classes;
    }
    
    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }
    
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                '}';
    }
} 