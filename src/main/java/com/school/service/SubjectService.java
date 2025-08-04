package com.school.service;

import com.school.entity.Subject;
import com.school.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubjectService {
    
    private final SubjectRepository subjectRepository;
    
    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    
    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }
    
    public Optional<Subject> getSubjectByName(String name) {
        return subjectRepository.findByName(name);
    }
    
    public Subject createSubject(Subject subject) {
        if (subjectRepository.existsByName(subject.getName())) {
            throw new RuntimeException("Subject with name '" + subject.getName() + "' already exists");
        }
        return subjectRepository.save(subject);
    }
    
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        
        subject.setName(subjectDetails.getName());
        subject.setDescription(subjectDetails.getDescription());
        subject.setCredits(subjectDetails.getCredits());
        
        return subjectRepository.save(subject);
    }
    
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
        
        // Check if subject is used in any classes
        if (!subject.getClasses().isEmpty()) {
            throw new RuntimeException("Cannot delete subject that is used in classes");
        }
        
        subjectRepository.delete(subject);
    }
    
    public boolean existsByName(String name) {
        return subjectRepository.existsByName(name);
    }
} 