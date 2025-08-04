package com.school.service;

import com.school.entity.Student;
import com.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    
    public List<Student> getStudentsByGradeLevel(Integer gradeLevel) {
        return studentRepository.findByGradeLevel(gradeLevel);
    }
    
    public List<Student> getStudentsWithGpaAbove(Double gpa) {
        return studentRepository.findByGpaGreaterThan(gpa);
    }
    
    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Student with email '" + student.getEmail() + "' already exists");
        }
        return studentRepository.save(student);
    }
    
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setPhone(studentDetails.getPhone());
        student.setDateOfBirth(studentDetails.getDateOfBirth());
        student.setEnrollmentDate(studentDetails.getEnrollmentDate());
        student.setAddress(studentDetails.getAddress());
        student.setGradeLevel(studentDetails.getGradeLevel());
        student.setGpa(studentDetails.getGpa());
        
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Check if student is enrolled in any classes
        if (!student.getClasses().isEmpty()) {
            throw new RuntimeException("Cannot delete student that is enrolled in classes");
        }
        
        studentRepository.delete(student);
    }
    
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
} 