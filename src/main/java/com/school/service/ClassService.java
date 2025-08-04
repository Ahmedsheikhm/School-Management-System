package com.school.service;

import com.school.entity.Class;
import com.school.entity.Student;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClassService {
    
    private final ClassRepository classRepository;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    
    @Autowired
    public ClassService(ClassRepository classRepository, SubjectService subjectService,
                       TeacherService teacherService, StudentService studentService) {
        this.classRepository = classRepository;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }
    
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }
    
    public Optional<Class> getClassById(Long id) {
        return classRepository.findById(id);
    }
    
    public List<Class> getClassesBySubject(Long subjectId) {
        Subject subject = subjectService.getSubjectById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + subjectId));
        return classRepository.findBySubject(subject);
    }
    
    public List<Class> getClassesByTeacher(Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + teacherId));
        return classRepository.findByTeacher(teacher);
    }
    
    public List<Class> getClassesByStudent(Long studentId) {
        return classRepository.findByStudentId(studentId);
    }
    
    public List<Class> getClassesBySemesterAndYear(String semester, String academicYear) {
        return classRepository.findBySemesterAndAcademicYear(semester, academicYear);
    }
    
    public List<Class> getAvailableClasses() {
        return classRepository.findAvailableClasses();
    }
    
    public Class createClass(Class classEntity) {
        // Validate subject exists using subjectId
        if (classEntity.getSubjectId() == null) {
            throw new RuntimeException("Subject ID is required");
        }
        Subject subject = subjectService.getSubjectById(classEntity.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + classEntity.getSubjectId()));
        classEntity.setSubject(subject);
        
        // Validate teacher exists using teacherId
        if (classEntity.getTeacherId() == null) {
            throw new RuntimeException("Teacher ID is required");
        }
        Teacher teacher = teacherService.getTeacherById(classEntity.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + classEntity.getTeacherId()));
        classEntity.setTeacher(teacher);
        
        return classRepository.save(classEntity);
    }
    
    public Class updateClass(Long id, Class classDetails) {
        Class classEntity = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        
        classEntity.setName(classDetails.getName());
        classEntity.setDescription(classDetails.getDescription());
        classEntity.setRoomNumber(classDetails.getRoomNumber());
        classEntity.setCapacity(classDetails.getCapacity());
        classEntity.setStartTime(classDetails.getStartTime());
        classEntity.setEndTime(classDetails.getEndTime());
        classEntity.setDaysOfWeek(classDetails.getDaysOfWeek());
        classEntity.setSemester(classDetails.getSemester());
        classEntity.setAcademicYear(classDetails.getAcademicYear());
        
        // Update subject if provided
        if (classDetails.getSubject() != null && classDetails.getSubject().getId() != null) {
            Subject subject = subjectService.getSubjectById(classDetails.getSubject().getId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            classEntity.setSubject(subject);
        }
        
        // Update teacher if provided
        if (classDetails.getTeacher() != null && classDetails.getTeacher().getId() != null) {
            Teacher teacher = teacherService.getTeacherById(classDetails.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            classEntity.setTeacher(teacher);
        }
        
        return classRepository.save(classEntity);
    }
    
    public void deleteClass(Long id) {
        Class classEntity = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        
        // Remove all students from the class
        classEntity.getStudents().clear();
        
        classRepository.delete(classEntity);
    }
    
    public void enrollStudent(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));
        
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        // Check if class is full
        if (classEntity.getStudents().size() >= classEntity.getCapacity()) {
            throw new RuntimeException("Class is full");
        }
        
        // Check if student is already enrolled
        if (classEntity.getStudents().contains(student)) {
            throw new RuntimeException("Student is already enrolled in this class");
        }
        
        classEntity.addStudent(student);
        classRepository.save(classEntity);
    }
    
    public void removeStudent(Long classId, Long studentId) {
        Class classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));
        
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        if (!classEntity.getStudents().contains(student)) {
            throw new RuntimeException("Student is not enrolled in this class");
        }
        
        classEntity.removeStudent(student);
        classRepository.save(classEntity);
    }
} 