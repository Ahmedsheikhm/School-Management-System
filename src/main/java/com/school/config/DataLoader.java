package com.school.config;

import com.school.entity.Class;
import com.school.entity.Student;
import com.school.entity.Subject;
import com.school.entity.Teacher;
import com.school.service.ClassService;
import com.school.service.StudentService;
import com.school.service.SubjectService;
import com.school.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ClassService classService;
    
    @Autowired
    public DataLoader(SubjectService subjectService, TeacherService teacherService,
                     StudentService studentService, ClassService classService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.classService = classService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Load sample data
        loadSampleData();
    }
    
    private void loadSampleData() {
        // Create subjects
        Subject math = new Subject("Mathematics", "Advanced mathematics including algebra, calculus, and geometry", 4);
        Subject physics = new Subject("Physics", "Fundamental principles of physics and mechanics", 3);
        Subject chemistry = new Subject("Chemistry", "Study of matter, its properties, and reactions", 3);
        Subject english = new Subject("English Literature", "Study of classic and contemporary literature", 3);
        Subject history = new Subject("World History", "Comprehensive study of world history", 3);
        
        subjectService.createSubject(math);
        subjectService.createSubject(physics);
        subjectService.createSubject(chemistry);
        subjectService.createSubject(english);
        subjectService.createSubject(history);
        
        // Create teachers
        Teacher teacher1 = new Teacher("John", "Smith", "john.smith@school.com", "+1234567890",
                LocalDate.of(1980, 5, 15), LocalDate.of(2010, 9, 1), "Mathematics", 65000.0);
        Teacher teacher2 = new Teacher("Sarah", "Johnson", "sarah.johnson@school.com", "+1234567891",
                LocalDate.of(1985, 8, 22), LocalDate.of(2012, 9, 1), "Physics", 62000.0);
        Teacher teacher3 = new Teacher("Michael", "Brown", "michael.brown@school.com", "+1234567892",
                LocalDate.of(1978, 3, 10), LocalDate.of(2008, 9, 1), "Chemistry", 68000.0);
        Teacher teacher4 = new Teacher("Emily", "Davis", "emily.davis@school.com", "+1234567893",
                LocalDate.of(1982, 12, 5), LocalDate.of(2015, 9, 1), "English Literature", 60000.0);
        Teacher teacher5 = new Teacher("David", "Wilson", "david.wilson@school.com", "+1234567894",
                LocalDate.of(1975, 7, 18), LocalDate.of(2005, 9, 1), "History", 63000.0);
        
        teacherService.createTeacher(teacher1);
        teacherService.createTeacher(teacher2);
        teacherService.createTeacher(teacher3);
        teacherService.createTeacher(teacher4);
        teacherService.createTeacher(teacher5);
        
        // Create students
        Student student1 = new Student("Alice", "Johnson", "alice.johnson@student.com", "+1987654321",
                LocalDate.of(2005, 4, 12), LocalDate.of(2020, 9, 1), "123 Main St, City", 10, 3.8);
        Student student2 = new Student("Bob", "Williams", "bob.williams@student.com", "+1987654322",
                LocalDate.of(2004, 11, 8), LocalDate.of(2019, 9, 1), "456 Oak Ave, City", 11, 3.5);
        Student student3 = new Student("Carol", "Miller", "carol.miller@student.com", "+1987654323",
                LocalDate.of(2006, 2, 25), LocalDate.of(2021, 9, 1), "789 Pine St, City", 9, 3.9);
        Student student4 = new Student("David", "Garcia", "david.garcia@student.com", "+1987654324",
                LocalDate.of(2003, 9, 3), LocalDate.of(2018, 9, 1), "321 Elm St, City", 12, 3.7);
        Student student5 = new Student("Eva", "Martinez", "eva.martinez@student.com", "+1987654325",
                LocalDate.of(2005, 7, 19), LocalDate.of(2020, 9, 1), "654 Maple Dr, City", 10, 3.6);
        
        studentService.createStudent(student1);
        studentService.createStudent(student2);
        studentService.createStudent(student3);
        studentService.createStudent(student4);
        studentService.createStudent(student5);
        
        // Create classes
        Class mathClass = new Class();
        mathClass.setName("Advanced Algebra");
        mathClass.setDescription("Advanced algebra concepts and problem solving");
        mathClass.setRoomNumber("Room 101");
        mathClass.setCapacity(25);
        mathClass.setStartTime(LocalTime.of(8, 0));
        mathClass.setEndTime(LocalTime.of(9, 30));
        mathClass.setDaysOfWeek("Monday,Wednesday,Friday");
        mathClass.setSemester("Fall");
        mathClass.setAcademicYear("2024-2025");
        mathClass.setSubjectId(1L);
        mathClass.setTeacherId(1L);
        
        Class physicsClass = new Class();
        physicsClass.setName("Physics Fundamentals");
        physicsClass.setDescription("Introduction to physics principles");
        physicsClass.setRoomNumber("Room 202");
        physicsClass.setCapacity(20);
        physicsClass.setStartTime(LocalTime.of(10, 0));
        physicsClass.setEndTime(LocalTime.of(11, 30));
        physicsClass.setDaysOfWeek("Tuesday,Thursday");
        physicsClass.setSemester("Fall");
        physicsClass.setAcademicYear("2024-2025");
        physicsClass.setSubjectId(2L);
        physicsClass.setTeacherId(2L);
        
        Class chemistryClass = new Class();
        chemistryClass.setName("General Chemistry");
        chemistryClass.setDescription("Basic chemistry concepts and laboratory work");
        chemistryClass.setRoomNumber("Room 203");
        chemistryClass.setCapacity(18);
        chemistryClass.setStartTime(LocalTime.of(13, 0));
        chemistryClass.setEndTime(LocalTime.of(14, 30));
        chemistryClass.setDaysOfWeek("Monday,Wednesday");
        chemistryClass.setSemester("Fall");
        chemistryClass.setAcademicYear("2024-2025");
        chemistryClass.setSubjectId(3L);
        chemistryClass.setTeacherId(3L);
        
        Class englishClass = new Class();
        englishClass.setName("Shakespeare Studies");
        englishClass.setDescription("In-depth study of Shakespeare's works");
        englishClass.setRoomNumber("Room 104");
        englishClass.setCapacity(22);
        englishClass.setStartTime(LocalTime.of(9, 0));
        englishClass.setEndTime(LocalTime.of(10, 30));
        englishClass.setDaysOfWeek("Tuesday,Thursday");
        englishClass.setSemester("Fall");
        englishClass.setAcademicYear("2024-2025");
        englishClass.setSubjectId(4L);
        englishClass.setTeacherId(4L);
        
        Class historyClass = new Class();
        historyClass.setName("Modern World History");
        historyClass.setDescription("Study of world history from 1500 to present");
        historyClass.setRoomNumber("Room 105");
        historyClass.setCapacity(24);
        historyClass.setStartTime(LocalTime.of(14, 0));
        historyClass.setEndTime(LocalTime.of(15, 30));
        historyClass.setDaysOfWeek("Monday,Wednesday,Friday");
        historyClass.setSemester("Fall");
        historyClass.setAcademicYear("2024-2025");
        historyClass.setSubjectId(5L);
        historyClass.setTeacherId(5L);
        
        classService.createClass(mathClass);
        classService.createClass(physicsClass);
        classService.createClass(chemistryClass);
        classService.createClass(englishClass);
        classService.createClass(historyClass);
        
        // Enroll students in classes
        classService.enrollStudent(1L, 1L); // Alice in Math
        classService.enrollStudent(1L, 2L); // Bob in Math
        classService.enrollStudent(1L, 3L); // Carol in Math
        
        classService.enrollStudent(2L, 1L); // Alice in Physics
        classService.enrollStudent(2L, 4L); // David in Physics
        classService.enrollStudent(2L, 5L); // Eva in Physics
        
        classService.enrollStudent(3L, 2L); // Bob in Chemistry
        classService.enrollStudent(3L, 3L); // Carol in Chemistry
        classService.enrollStudent(3L, 4L); // David in Chemistry
        
        classService.enrollStudent(4L, 1L); // Alice in English
        classService.enrollStudent(4L, 5L); // Eva in English
        
        classService.enrollStudent(5L, 2L); // Bob in History
        classService.enrollStudent(5L, 4L); // David in History
        classService.enrollStudent(5L, 5L); // Eva in History
        
        System.out.println("Sample data loaded successfully!");
    }
} 