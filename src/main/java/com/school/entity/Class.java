package com.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classes")
public class Class {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Class name is required")
    @Size(min = 2, max = 100, message = "Class name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description")
    private String description;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "capacity")
    private Integer capacity;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @Column(name = "days_of_week")
    private String daysOfWeek; // e.g., "Monday,Wednesday,Friday"
    
    @Column(name = "semester")
    private String semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonIgnore
    private Subject subject;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    @JsonIgnore
    private Teacher teacher;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "class_students",
        joinColumns = @JoinColumn(name = "class_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnore
    private Set<Student> students = new HashSet<>();
    
    // JSON properties for deserialization
    @JsonProperty("subjectId")
    @Transient
    private Long subjectId;
    
    @JsonProperty("teacherId")
    @Transient
    private Long teacherId;
    
    // Constructors
    public Class() {}
    
    public Class(String name, String description, String roomNumber, Integer capacity,
                 LocalTime startTime, LocalTime endTime, String daysOfWeek,
                 String semester, String academicYear, Subject subject, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.daysOfWeek = daysOfWeek;
        this.semester = semester;
        this.academicYear = academicYear;
        this.subject = subject;
        this.teacher = teacher;
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
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public String getDaysOfWeek() {
        return daysOfWeek;
    }
    
    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public String getAcademicYear() {
        return academicYear;
    }
    
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public Set<Student> getStudents() {
        return students;
    }
    
    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    
    // JSON property getters and setters
    public Long getSubjectId() {
        if (subject != null) {
            return subject.getId();
        }
        return subjectId;
    }
    
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    
    public Long getTeacherId() {
        if (teacher != null) {
            return teacher.getId();
        }
        return teacherId;
    }
    
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    
    // Helper methods
    public void addStudent(Student student) {
        this.students.add(student);
        student.getClasses().add(this);
    }
    
    public void removeStudent(Student student) {
        this.students.remove(student);
        student.getClasses().remove(this);
    }
    
    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", capacity=" + capacity +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", daysOfWeek='" + daysOfWeek + '\'' +
                ", semester='" + semester + '\'' +
                ", academicYear='" + academicYear + '\'' +
                ", subject=" + (subject != null ? subject.getName() : "null") +
                ", teacher=" + (teacher != null ? teacher.getFirstName() + " " + teacher.getLastName() : "null") +
                '}';
    }
} 