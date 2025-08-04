package com.school.service;

import com.school.entity.Teacher;
import com.school.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherService {
    
    private final TeacherRepository teacherRepository;
    
    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
    
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }
    
    public Optional<Teacher> getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }
    
    public Teacher createTeacher(Teacher teacher) {
        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new RuntimeException("Teacher with email '" + teacher.getEmail() + "' already exists");
        }
        return teacherRepository.save(teacher);
    }
    
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        
        teacher.setFirstName(teacherDetails.getFirstName());
        teacher.setLastName(teacherDetails.getLastName());
        teacher.setEmail(teacherDetails.getEmail());
        teacher.setPhone(teacherDetails.getPhone());
        teacher.setDateOfBirth(teacherDetails.getDateOfBirth());
        teacher.setHireDate(teacherDetails.getHireDate());
        teacher.setSpecialization(teacherDetails.getSpecialization());
        teacher.setSalary(teacherDetails.getSalary());
        
        return teacherRepository.save(teacher);
    }
    
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        
        // Check if teacher is assigned to any classes
        if (!teacher.getClasses().isEmpty()) {
            throw new RuntimeException("Cannot delete teacher that is assigned to classes");
        }
        
        teacherRepository.delete(teacher);
    }
    
    public boolean existsByEmail(String email) {
        return teacherRepository.existsByEmail(email);
    }
} 