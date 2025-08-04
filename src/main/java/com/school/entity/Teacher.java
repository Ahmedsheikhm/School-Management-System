package com.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Email(message = "Email should be valid")
    @Column(name = "email", unique = true)
    private String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number should be valid")
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @Size(max = 200, message = "Specialization cannot exceed 200 characters")
    @Column(name = "specialization")
    private String specialization;
    
    @Column(name = "salary")
    private Double salary;
    
    // Relationships
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Class> classes = new HashSet<>();
    
    // Constructors
    public Teacher() {}
    
    public Teacher(String firstName, String lastName, String email, String phone, 
                   LocalDate dateOfBirth, LocalDate hireDate, String specialization, Double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.hireDate = hireDate;
        this.specialization = specialization;
        this.salary = salary;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public Double getSalary() {
        return salary;
    }
    
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public Set<Class> getClasses() {
        return classes;
    }
    
    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }
    
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", hireDate=" + hireDate +
                ", specialization='" + specialization + '\'' +
                ", salary=" + salary +
                '}';
    }
} 